package com.BillSyncOrg.BillSync.util;

import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;

/**
 * Global exception handler for the entire application.
 * <p>
 * Captures and returns meaningful error messages for:
 * <ul>
 *     <li>Validation errors in incoming requests</li>
 *     <li>Custom business logic exceptions such as {@link BillSyncClientException}</li>
 * </ul>
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles BillSyncClientException thrown during sign-up.
   *
   * @param ex the exception thrown
   * @return a structured error response with HTTP 400 status
   */
  @ExceptionHandler(BillSyncClientException.class)
  public ResponseEntity<Object> handleBillSyncClientException(BillSyncClientException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return ResponseGenerator.builder().message(ex.getMessage())
      .status(ex.getHttpStatusCode()).build();
  }

  /**
   * Handles BillSyncServerException thrown during sign-up.
   *
   * @param ex the exception thrown
   * @return a structured error response with HTTP 400 status
   */
  @ExceptionHandler(BillSyncServerException.class)
  public ResponseEntity<Object> handleBillSyncServerException(BillSyncServerException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("error", ex.getMessage());
    return ResponseGenerator.builder().message(ex.getMessage())
      .status(ex.getHttpStatusCode()).build();
  }

  /**
   * Handles validation errors triggered by {@code @Valid} in controllers.
   *
   * @param ex the validation exception
   * @return a map of field-specific error messages with HTTP 400 status
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(err ->
      errors.put(err.getField(), err.getDefaultMessage())
    );
    return ResponseGenerator.builder().body(errors).message(errors.toString())
      .status(HttpStatusCodeEnum.BAD_REQUEST).build();
  }
}
