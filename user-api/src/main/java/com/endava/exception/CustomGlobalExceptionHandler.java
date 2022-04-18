package com.endava.exception;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.endava.util.Constant.ERRORS;
import static com.endava.util.Constant.STATUS;
import static com.endava.util.Constant.TIMESTAMP;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleEntityNotFoundException(Exception exception) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimeStamp(LocalDateTime.now());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setError(exception.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<CustomErrorResponse> customHandleCustomRuntimeException(Exception exception) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimeStamp(LocalDateTime.now());
        errors.setError(exception.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> customHandleConstraintViolationException(Exception exception, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimeStamp(LocalDateTime.now());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setError(exception.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<CustomErrorResponse> customHandlePropertyReferenceExceptionException(Exception exception, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimeStamp(LocalDateTime.now());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setError(exception.getMessage().substring(0, exception.getMessage().indexOf("!") + 1));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        Map<String, Object> body = fillBody(status, ex);
        return new ResponseEntity<>(body, headers, status);
    }


    // error handle for @Valid
    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatus status, WebRequest request) {
        Map<String, Object> body = fillBody(status, ex);
        return new ResponseEntity<>(body, headers, status);
    }

    private Map<String, Object> fillBody(HttpStatus status, BindException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, new Date());
        body.put(STATUS, status);
        body.put(ERRORS, getFieldErrors(ex));
        return body;
    }

    private Map<String, List<String>> getFieldErrors(MethodArgumentNotValidException ex) {
        Map<String, List<String>> fieldErrors = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            fieldErrors.putIfAbsent(fieldError.getField(), new ArrayList<>());
            fieldErrors.get(fieldError.getField()).add(fieldError.getDefaultMessage());
        });
        return fieldErrors;
    }


    private Map<String, List<String>> getFieldErrors(BindException ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            return getFieldErrors((MethodArgumentNotValidException) ex);
        }

        Map<String, List<String>> fieldErrors = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            fieldErrors.putIfAbsent(fieldError.getField(), new ArrayList<>());
            fieldErrors.get(fieldError.getField()).add(fieldError.getRejectedValue() + " is not valid for field " + fieldError.getField());
        });
        return fieldErrors;
    }

}
