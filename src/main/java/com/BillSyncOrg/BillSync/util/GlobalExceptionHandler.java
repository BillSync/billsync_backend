package com.BillSyncOrg.BillSync.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

import com.BillSyncOrg.BillSync.exceptions.BillSyncException;

/**
 * Global exception handler for the entire application.
 * <p>
 * Captures and returns meaningful error messages for:
 * <ul>
 *     <li>Validation errors in incoming requests</li>
 *     <li>Custom business logic exceptions such as {@link BillSyncException}</li>
 * </ul>
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles BillSyncException thrown during sign-up.
   *
   * @param ex the exception thrown
   * @return a structured error response with HTTP 400 status
   */
  @ExceptionHandler(BillSyncException.class)
  public ResponseEntity<Map<String, String>> handleBillSyncException(BillSyncException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles validation errors triggered by {@code @Valid} in controllers.
   *
   * @param ex the validation exception
   * @return a map of field-specific error messages with HTTP 400 status
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(err ->
      errors.put(err.getField(), err.getDefaultMessage())
    );
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
