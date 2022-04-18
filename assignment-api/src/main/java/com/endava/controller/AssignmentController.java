package com.endava.controller;

import com.endava.dto.AssignmentDto;
import com.endava.dto.AssignmentResponse;
import com.endava.dto.ExtendAssignmentDto;
import com.endava.dto.RequestAssignmentDto;
import com.endava.model.Assignment;
import com.endava.service.impl.AssignmentServiceImpl;
import com.endava.specification.AssignmentSpecificationParams;
import com.endava.specification.BookSpecificationParams;
import com.endava.specification.UserSpecificationParams;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.endava.util.Constant.SUCCESSFULLY_EXTENDED_ASSIGNMENT;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    private final AssignmentServiceImpl assignmentService;
    private final ModelMapper modelMapper;

    public AssignmentController(AssignmentServiceImpl assignmentService, ModelMapper modelMapper) {
        this.assignmentService = assignmentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public AssignmentDto assignBookToUser(@Valid @RequestBody RequestAssignmentDto requestAssignmentDto) {
        Assignment assignment = assignmentService.createAssignment(requestAssignmentDto);
        return modelMapper.map(assignment, AssignmentDto.class);
    }

    @GetMapping("/internal/user-id/{id}")
    public boolean isUserHaveAssignments(@PathVariable("id") Long id) {
        List<Assignment> assignments = assignmentService.getAssignmentsByUserId(id);
        return assignments.size() > 0;
    }

    @GetMapping("/internal/book-id/{id}")
    public boolean isBookHaveAssignment(@PathVariable("id") Long bookId) {
        return assignmentService.findAssignmentBook(bookId);
    }

    @GetMapping("/all-active-bookings")
    public ResponseEntity<AssignmentResponse> allActiveBookings(
            UserSpecificationParams userSpecificationParams, BookSpecificationParams bookSpecificationParams,
            AssignmentSpecificationParams assignmentSpecificationParams,
            Pageable pageable) {
        AssignmentResponse response = this.assignmentService.
                getAllActiveBookings(userSpecificationParams, bookSpecificationParams, assignmentSpecificationParams, pageable);
        return ResponseEntity.ok(response);
    }
    @PatchMapping("/extend")
    public String extendAssignment(@Valid @RequestBody ExtendAssignmentDto extendAssignmentDto){
        assignmentService.extendAssignment(extendAssignmentDto);
        return String.format(SUCCESSFULLY_EXTENDED_ASSIGNMENT, extendAssignmentDto.getDaysToExtendBy());
    }

}
