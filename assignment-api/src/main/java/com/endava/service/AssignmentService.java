package com.endava.service;

import com.endava.dto.ExtendAssignmentDto;
import com.endava.dto.AssignmentResponse;
import com.endava.dto.RequestAssignmentDto;
import com.endava.model.Assignment;
import com.endava.specification.AssignmentSpecificationParams;
import com.endava.specification.BookSpecificationParams;
import com.endava.specification.UserSpecificationParams;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentService {
    Assignment createAssignment(RequestAssignmentDto requestAssignmentDto);
    Assignment extendAssignment(ExtendAssignmentDto extendAssignmentDto);

    List<Assignment> getAssignmentsByUserId(Long id);

    boolean findAssignmentBook(Long bookId);

    AssignmentResponse getAllActiveBookings(UserSpecificationParams userSpecificationParams,
                                            BookSpecificationParams bookSpecificationParams,
                                            AssignmentSpecificationParams assignmentSpecificationParams,
                                            Pageable pageable);
}
