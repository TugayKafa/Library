package com.endava.client.assignment;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assignmentClient", url = "${assignment-api.url}")
public interface AssignmentClient {

    @GetMapping(value = "/assignments/internal/book-id/{id}")
    boolean isBookHaveAssignment(@PathVariable("id") Long id);
}