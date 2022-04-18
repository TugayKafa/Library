package com.endava.service.impl;

import com.endava.client.book.BookClient;
import com.endava.client.user.UserClient;
import com.endava.dto.ExtendAssignmentDto;
import com.endava.dto.AssignmentResponse;
import com.endava.dto.PageAssignmentResponse;
import com.endava.dto.RequestAssignmentDto;
import com.endava.dto.book.BookDetails;
import com.endava.dto.external.ExternalBookDto;
import com.endava.dto.external.ExternalUserDto;
import com.endava.dto.user.UserDetails;
import com.endava.dto.user.UserResponseDetails;
import com.endava.excepion.AssignmentSearchTextNumberFormatException;
import com.endava.excepion.AssignmentDoesntExistException;
import com.endava.excepion.BookAlreadyAssignedException;
import com.endava.excepion.BookLimitExceededException;
import com.endava.excepion.BookOutOfStockException;
import com.endava.excepion.BookRecentlyAssignedException;
import com.endava.excepion.IncorrectSortValueException;
import com.endava.excepion.MaximumPeriodExceededException;
import com.endava.excepion.UserInactiveException;
import com.endava.model.Assignment;
import com.endava.model.user.Status;
import com.endava.repository.AssignmentHistoryRepository;
import com.endava.repository.AssignmentRepository;
import com.endava.service.AssignmentService;
import com.endava.sort.Command;
import com.endava.sort.SortByTimeLeftTillExpiration;
import com.endava.sort.SortPropertyFactory;
import com.endava.specification.AssignmentSpecificationParams;
import com.endava.specification.BookSpecificationParams;
import com.endava.specification.UserSpecificationParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.endava.util.Constant.FIFTEEN_DAYS_BEFORE_TODAY_DATE;
import static com.endava.util.Constant.MAXIMUM_ASSIGNMENTS;
import static com.endava.util.Constant.TODAY_DATE;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final UserClient userClient;
    private final BookClient bookClient;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentHistoryRepository assignmentHistoryRepository;

    public AssignmentServiceImpl(UserClient userClient, BookClient bookClient, AssignmentRepository assignmentRepository, AssignmentHistoryRepository assignmentHistoryRepository) {
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.assignmentRepository = assignmentRepository;
        this.assignmentHistoryRepository = assignmentHistoryRepository;
    }

    @Override
    public Assignment createAssignment(RequestAssignmentDto requestAssignmentDto) {

        ExternalUserDto user = userClient.getUserByEgn(requestAssignmentDto.getUserDto());

        Set<ExternalBookDto> booksToAssign = bookClient.getBookSetByIsbnSet(requestAssignmentDto.getIsbnSet());

        List<Assignment> assignments = assignmentRepository.findByUserId(user.getId());

        List<Long> bookIdsAlreadyAssigned = assignments.stream()
                .flatMap(assignment -> assignment.getAssignmentBooks().stream())
                .collect(Collectors.toList());

        List<Long> bookIdsToAssign = booksToAssign.stream().map(ExternalBookDto::getId).collect(Collectors.toList());

        if (bookIdsAlreadyAssigned.size() + bookIdsToAssign.size() > MAXIMUM_ASSIGNMENTS) {
            throw new BookLimitExceededException();
        }

        if (!Objects.equals(user.getStatus(), Status.ACTIVE)) {
            throw new UserInactiveException();
        }

        if (getCountOfBooksThatCannotBeAssigned(bookIdsToAssign) > 0) {
            throw new BookRecentlyAssignedException();
        }

        if (!Collections.disjoint(bookIdsToAssign, bookIdsAlreadyAssigned)) {
            throw new BookAlreadyAssignedException();
        }

        if (!validateBookQuantity(booksToAssign)) {
            throw new BookOutOfStockException();
        }

        Assignment assignment = new Assignment();
        assignment.setUserId(user.getId());
        assignment.setAssignmentBooks(booksToAssign.stream().map(ExternalBookDto::getId).collect(Collectors.toList()));
        assignment.setAssignmentDate(LocalDate.now());
        assignment.setPeriodInWeeks(requestAssignmentDto.getPeriodInWeeks());
        assignment.setReturnDate(
                LocalDate.now().plusWeeks(
                        requestAssignmentDto.getPeriodInWeeks()
                )
        );

        bookClient.updateBookQuantity(bookIdsToAssign);

        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment extendAssignment(ExtendAssignmentDto extendAssignmentDto) {

        ExternalUserDto user = userClient.getUserByEgn(extendAssignmentDto.getUserDto());

        ExternalBookDto book = bookClient.getBookByIsbn(extendAssignmentDto.getIsbn());

        Assignment assignment = assignmentRepository.findByUserIdAndBookId(
                user.getId(), book.getId()
        ).orElseThrow(
                AssignmentDoesntExistException::new
        );

        LocalDate dateToUpdate = assignment.getReturnDate();
        LocalDate assignedDate = assignment.getAssignmentDate();

        if (assignedDate.plusMonths(3).isBefore(dateToUpdate.plusDays(extendAssignmentDto.getDaysToExtendBy()))) {
            throw new MaximumPeriodExceededException();
        }

        assignment.setReturnDate(assignment.getReturnDate().plusDays(extendAssignmentDto.getDaysToExtendBy()));

        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAssignmentsByUserId(Long id) {
        return assignmentRepository.findByUserId(id);
    }

    @Override
    public boolean findAssignmentBook(Long bookId) {
        return this.assignmentRepository.findBookById(bookId) != 0;
    }

    private Long getCountOfBooksThatCannotBeAssigned(List<Long> bookIdsToAssign) {
        return assignmentHistoryRepository.findAssignedBookHistoryCountByReturnDateAndUserId(
                FIFTEEN_DAYS_BEFORE_TODAY_DATE,
                TODAY_DATE,
                7L,
                bookIdsToAssign
        );
    }

    private boolean validateBookQuantity(Set<ExternalBookDto> books) {
        return books.stream().noneMatch(book -> book.getQuantity() < 1);
    }

    @Override
    public AssignmentResponse getAllActiveBookings(UserSpecificationParams userSpecificationParams,
                                                   BookSpecificationParams bookSpecificationParams,
                                                   AssignmentSpecificationParams assignmentSpecificationParams,
                                                   Pageable pageable) {

        AssignmentResponse assignmentResponse = new AssignmentResponse();
        List<PageAssignmentResponse> pageAssignmentResponses =
                mapAllActiveBookingsToPageAssignmentResponse(userSpecificationParams,
                        bookSpecificationParams, assignmentSpecificationParams, assignmentResponse);
        sortAllActiveBookings(pageAssignmentResponses, pageable.getSort());
        pageAllActiveBookings(pageable, assignmentResponse, pageAssignmentResponses);
        return assignmentResponse;
    }

    private List<PageAssignmentResponse>
    mapAllActiveBookingsToPageAssignmentResponse(UserSpecificationParams userSpecificationParams,
                                                 BookSpecificationParams bookSpecificationParams,
                                                 AssignmentSpecificationParams assignmentSpecificationParams,
                                                 AssignmentResponse assignmentResponse) {

        Integer totalActiveBookAssignment = this.getTotalActiveBookAssignments();
        Integer totalUniqueAssignedBooks = this.getTotalUniqueAssignedBooks();
        Integer totalUniqueUsersWithActiveAssignments = this.getTotalUniqueUsersWithActiveAssignments();
        assignmentResponse.setTotalActiveBookAssignment(totalActiveBookAssignment);
        assignmentResponse.setTotalUniqueAssignedBooks(totalUniqueAssignedBooks);
        assignmentResponse.setTotalUniqueUsersWithActiveAssignments(totalUniqueUsersWithActiveAssignments);
        List<PageAssignmentResponse> pageAssignmentResponses = new ArrayList<>();

        if (assignmentSpecificationParams.getAssignmentSearchText() != null ||
                assignmentSpecificationParams.getAssignmentSearchType() == null) {
            List<Assignment> assignments = assignmentRepository.findAll();
            List<Long> usersId;
            usersId = getUsersId(assignmentSpecificationParams, assignments);
            assignmentUserWithBooks(userSpecificationParams, bookSpecificationParams, assignmentSpecificationParams,
                    pageAssignmentResponses, usersId);
        }
        return pageAssignmentResponses;
    }

    private List<Long> getUsersId(AssignmentSpecificationParams assignmentSpecificationParams,
                                  List<Assignment> assignments) {
        List<Long> usersId = new ArrayList<>();
        long daysBetween;
        for (Assignment assignment : assignments) {
            if (assignmentSpecificationParams.getAssignmentSearchType() == null ||
                    assignmentSpecificationParams.getAssignmentSearchText() == null) {
                usersId.add(assignment.getUserId());
            } else {
                daysBetween = DAYS.between(LocalDate.now(), assignment.getReturnDate());
                try {
                    if (daysBetween < Integer.parseInt(assignmentSpecificationParams.getAssignmentSearchText())) {
                        usersId.add(assignment.getUserId());
                    }
                } catch (NumberFormatException exception) {
                    throw new AssignmentSearchTextNumberFormatException();
                }
            }
        }
        return usersId;
    }

    private void assignmentUserWithBooks(UserSpecificationParams userSpecificationParams,
                                         BookSpecificationParams bookSpecificationParams,
                                         AssignmentSpecificationParams assignmentSpecificationParams,
                                         List<PageAssignmentResponse> pageAssignmentResponses,
                                         List<Long> usersId) {

        long daysBetween;
        List<UserDetails> userDetails = userClient.getUserSpecification(usersId, userSpecificationParams);
        for (UserDetails userDetail : userDetails) {
            List<Assignment> byUserId = this.assignmentRepository.findByUserId(userDetail.getId());
            for (Assignment assignment : byUserId) {
                daysBetween = DAYS.between(LocalDate.now(), assignment.getReturnDate());
                if (assignmentSpecificationParams.getAssignmentSearchType() == null
                        || assignmentSpecificationParams.getAssignmentSearchText() == null
                        || daysBetween < Integer.parseInt(assignmentSpecificationParams.getAssignmentSearchText())) {
                    usersId.add(assignment.getUserId());
                    PageAssignmentResponse pageAssignmentResponse = new PageAssignmentResponse();
                    UserResponseDetails userResponseDetails = new UserResponseDetails();
                    userResponseDetails.setEmail(userDetail.getEmail());
                    userResponseDetails.setFullName(userDetail.getFullName());
                    pageAssignmentResponse.setUserDetails(userResponseDetails);
                    pageAssignmentResponse.setAssignmentDate(assignment.getAssignmentDate().toString());
                    pageAssignmentResponse.setReturnDate(assignment.getReturnDate().toString());
                    pageAssignmentResponse.setTimeLeftTillExpiration(this.getTimeLeftTillExpiration(LocalDate.now(), assignment.getReturnDate()));
                    List<BookDetails> allActiveBook = bookClient.getBookSpecifications(bookSpecificationParams, assignment.getAssignmentBooks());
                    if (!allActiveBook.isEmpty()) {
                        pageAssignmentResponse.setBookDetails(allActiveBook);
                        pageAssignmentResponses.add(pageAssignmentResponse);
                    }
                }
            }
        }
    }

    private void pageAllActiveBookings(Pageable pageable, AssignmentResponse assignmentResponse, List<PageAssignmentResponse> pageAssignmentResponses) {
        int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), pageAssignmentResponses.size());
        if (start > end) {
            start = end;
        }
        Page<PageAssignmentResponse> page = new PageImpl<>(pageAssignmentResponses.subList(start, end), pageable, pageAssignmentResponses.size());
        assignmentResponse.setPageDetails(page);
    }

    private void sortAllActiveBookings(List<PageAssignmentResponse> pageAssignmentResponses, Sort sort) {
        if (sort.isEmpty()) {
            new SortByTimeLeftTillExpiration().sort(pageAssignmentResponses, Sort.Direction.ASC, PageAssignmentResponse::getTimeLeftTillExpiration);
        } else {
            for (Sort.Order order : sort) {
                Command command = new SortPropertyFactory().getSortCommand(order.getProperty());
                try {
                    command.sort(pageAssignmentResponses, order.getDirection(), command.getFunction());
                } catch (NullPointerException exception) {
                    throw new IncorrectSortValueException();
                }
            }
        }
    }

    private String getTimeLeftTillExpiration(LocalDate date, LocalDate returnDate) {
        long daysBetween = DAYS.between(date, returnDate);
        if (daysBetween < 1) {
            return "EOD";
        } else {
            if (daysBetween < 2) {
                return daysBetween + " day";
            } else {
                return daysBetween + " days";
            }
        }
    }

    private Integer getTotalActiveBookAssignments() {
        AtomicInteger count = new AtomicInteger();
        this.assignmentRepository.findAll().stream()
                .map(Assignment::getAssignmentBooks)
                .forEach(assignmentBooks -> count.addAndGet(assignmentBooks.size()));
        return count.get();
    }

    private Integer getTotalUniqueAssignedBooks() {
        Set<Long> totalUniqueAssignedBooks = new HashSet<>();
        this.assignmentRepository.findAll().stream()
                .flatMap(assignment -> Stream.of(assignment.getAssignmentBooks()))
                .forEach(totalUniqueAssignedBooks::addAll);
        return totalUniqueAssignedBooks.size();
    }

    private Integer getTotalUniqueUsersWithActiveAssignments() {
        return this.assignmentRepository.countDistinctUserId();
    }
}
