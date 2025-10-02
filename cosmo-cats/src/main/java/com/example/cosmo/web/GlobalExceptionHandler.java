package com.example.cosmo.web;

import com.example.cosmo.common.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    List<Map<String, Object>> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(this::toFieldError)
        .toList();

    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
    pd.setTitle("Bad Request");
    pd.setType(URI.create("about:blank"));
    pd.setProperty("errors", errors);

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("type", pd.getType().toString());
    body.put("title", pd.getTitle());
    body.put("status", pd.getStatus());
    body.put("detail", "Validation failed for object: " + ex.getObjectName());
    body.put("instance", req.getRequestURI());
    body.put("timestamp", OffsetDateTime.now().toString());

    body.put("error", "Bad Request");
    body.put("message", firstOrDefault(errors)
        .map(e -> "Field '%s' %s".formatted(e.get("field"), e.get("message")))
        .orElse("Validation error"));
    body.put("path", req.getRequestURI());
    body.put("errors", errors);
    return body;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    pd.setTitle("Bad Request");
    return problemToBody(pd, req, "Validation constraint violated");
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, Object> handleNotFound(NotFoundException ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    pd.setTitle("Not Found");
    return problemToBody(pd, req, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> handleOther(Exception ex, HttpServletRequest req) {
    ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    pd.setTitle("Internal Server Error");
    return problemToBody(pd, req, ex.getMessage());
  }

  private Map<String, Object> toFieldError(FieldError fe) {
    Map<String, Object> m = new LinkedHashMap<>();
    m.put("field", fe.getField());
    m.put("rejectedValue", fe.getRejectedValue());
    m.put("message", fe.getDefaultMessage());
    return m;
  }

  private Map<String, Object> problemToBody(ProblemDetail pd, HttpServletRequest req, String message) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("type", Optional.ofNullable(pd.getType()).map(URI::toString).orElse("about:blank"));
    body.put("title", pd.getTitle());
    body.put("status", pd.getStatus());
    body.put("detail", pd.getDetail());
    body.put("instance", req.getRequestURI());
    body.put("timestamp", OffsetDateTime.now().toString());
    body.put("error", pd.getTitle());
    body.put("message", message);
    body.put("path", req.getRequestURI());
    for (String key : pd.getProperties().keySet()) {
      body.putIfAbsent(key, pd.getProperties().get(key));
    }
    return body;
  }

  private Optional<Map<String, Object>> firstOrDefault(List<Map<String, Object>> list) {
    return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
  }
}
