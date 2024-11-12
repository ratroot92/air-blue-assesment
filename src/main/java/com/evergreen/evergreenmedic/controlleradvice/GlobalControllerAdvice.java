package com.evergreen.evergreenmedic.controlleradvice;

import com.evergreen.evergreenmedic.errors.CustomFieldError;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.NOT_FOUND.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.NOT_FOUND.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errResponse);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(Exception ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.UNAUTHORIZED.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.UNAUTHORIZED.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.BAD_REQUEST.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.BAD_REQUEST.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.UNAUTHORIZED.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.UNAUTHORIZED.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errResponse);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Object> handleUnexpectedTypeException(UnexpectedTypeException ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<Object> handleClassCastException(ClassCastException ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {
        HashMap<String, String> errResponse = new HashMap<>();
        System.out.println(Arrays.stream(ex.getStackTrace()).toArray().toString());
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<Object> handleServletException(ServletException ex, WebRequest request) {
        log.info("ServletException occurred: {}", ex.getMessage());
        log.info("ServletException occurred: {}", ex.getStackTrace());
        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errResponse);
    }

//
// ServletException
//    NullPointerException


//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
//
//        HashMap<String, String> errResponse = new HashMap<>();
//        errResponse.put("exception", ex.getClass().getName());
//        errResponse.put("status", HttpStatus.BAD_REQUEST.name());
//        errResponse.put("error", ex.getMessage());
//        errResponse.put("statusCode", HttpStatus.BAD_REQUEST.toString());
//        errResponse.put("stackTrace", ex.getStackTrace().toArray().toString());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
//    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {

        HashMap<String, String> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.BAD_REQUEST.name());
        errResponse.put("error", ex.getMessage());
        errResponse.put("statusCode", HttpStatus.BAD_REQUEST.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<CustomFieldError> errorsLists = new ArrayList<CustomFieldError>();

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors().stream().map(f -> {
            CustomFieldError customFieldError = new CustomFieldError();
            customFieldError.setFieldName(f.getField());
            customFieldError.setFieldMessage(f.getField() + " " + f.getDefaultMessage());
            errorsLists.add(customFieldError);
            return new FieldError(f.getField(), f.getCode(), f.getDefaultMessage());
        }).collect(Collectors.toList());
        HashMap<String, Object> errResponse = new HashMap<>();
        errResponse.put("exception", ex.getClass().getName());
        errResponse.put("status", HttpStatus.UNPROCESSABLE_ENTITY.name());
        errResponse.put("error", errorsLists);
        errResponse.put("statusCode", HttpStatus.UNPROCESSABLE_ENTITY.toString());
        errResponse.put("stackTrace", Arrays.stream(ex.getStackTrace()).toArray().toString());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errResponse);

    }
}
