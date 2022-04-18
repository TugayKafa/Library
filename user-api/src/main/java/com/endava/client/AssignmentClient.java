package com.endava.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assignmentClient", url = "${assignment-api.url}")
public interface AssignmentClient {

    @GetMapping("/assignments/internal/user-id/{id}")
    boolean isUserHaveAssignments(@PathVariable("id") Long id);

}
