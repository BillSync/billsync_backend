package com.BillSyncOrg.BillSync.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpHeaders;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for generating HTTP responses using the Builder design pattern.
 * It allows developers to construct a standardized {@link ResponseEntity} by specifying
 * status, body, and optional headers or custom messages.
 *
 * <p>This class improves reusability and consistency in controller/service layer responses.</p>
 */
public class ResponseGenerator {

  private ResponseGenerator() throws IllegalAccessException {
    throw new IllegalAccessException("Cannot access private constructor");
  }

  /**
   * Initiates a new builder for constructing a ResponseEntity.
   *
   * @return a new instance of Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder class for ResponseGenerator. Use this to create ResponseEntity objects step-by-step.
   */
  public static class Builder {

    private HttpStatusCodeEnum statusCode;
    private Object body;
    private String customMessage;

    /**
     * Sets the HTTP status for the response.
     *
     * @param statusCode Custom HttpStatusCodeEnum
     * @return this builder instance
     */
    public Builder status(HttpStatusCodeEnum statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    /**
     * Sets the body of the response.
     *
     * @param body the response body
     * @return this builder instance
     */
    public Builder body(Object body) {
      this.body = body;
      return this;
    }

    /**
     * Sets a custom message, which will be included alongside the response body.
     *
     * @param message a human-readable message
     * @return this builder instance
     */
    public Builder message(String message) {
      this.customMessage = message;
      return this;
    }

    /**
     * Constructs the final {@link ResponseEntity<Object>} using the configured values.
     *
     * @return a fully constructed ResponseEntity
     */
    public ResponseEntity<Object> build() {

      Map<String, Object> responseBody = new LinkedHashMap<>();
      responseBody.put("status", this.statusCode.getCode());
      responseBody.put("message", this.customMessage != null ? this.customMessage : this.statusCode.getReason());
      responseBody.put("data", this.body);

      return new ResponseEntity<>(responseBody, HttpStatus.valueOf(this.statusCode.getCode()));
    }
  }
}
