package com.chicksgold.WaterBucket.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
    Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMostSpecificCause().getMessage());

        Map<String, Object> body = new HashMap<>();
        ApiException apiError =
                new ApiException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<>(apiError, headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> applyException(EmptyResultDataAccessException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({NoSolutionPossibleException.class})
    @ResponseBody
    public ResponseEntity<Object> handleResourcesFound(Exception exception) {
        ApiException apiException = ApiException.builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .errors(List.of(getRootCauseMessage(exception)))
                .build();
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }
    private String getRootCauseMessage(Throwable throwable) {
        Throwable rootCause = getRootCause(throwable);
        return rootCause != null ? rootCause.getMessage() : throwable.getMessage();
    }
    private Throwable getRootCause(Throwable throwable) {
        Throwable cause = null;
        Throwable result = throwable;

        while ((cause = result.getCause()) != null && result != cause) {
            result = cause;
        }
        return result;
    }
}
