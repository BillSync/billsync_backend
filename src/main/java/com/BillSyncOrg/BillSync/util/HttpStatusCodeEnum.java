package com.BillSyncOrg.BillSync.util;

/**
 * Enum representing standard HTTP status codes ranging from 200 to 503.
 * Each status includes a numeric code and a human-readable reason phrase.
 * This enum can be used to provide consistent response status handling across services and exceptions.
 */
public enum HttpStatusCodeEnum {

  /**
   * 200 OK: The request has succeeded.
   */
  OK(200, "OK"),

  /**
   * 201 Created: The request has been fulfilled and resulted in a new resource being created.
   */
  CREATED(201, "Created"),

  /**
   * 202 Accepted: The request has been accepted for processing, but the processing is not complete.
   */
  ACCEPTED(202, "Accepted"),

  /**
   * 204 No Content: The server has successfully fulfilled the request and there is no additional content to send.
   */
  NO_CONTENT(204, "No Content"),

  /**
   * 400 Bad Request: The server cannot process the request due to something that is perceived to be a client error.
   */
  BAD_REQUEST(400, "Bad Request"),

  /**
   * 401 Unauthorized: The request has not been applied because it lacks valid authentication credentials.
   */
  UNAUTHORIZED(401, "Unauthorized"),

  /**
   * 403 Forbidden: The server understood the request but refuses to authorize it.
   */
  FORBIDDEN(403, "Forbidden"),

  /**
   * 404 Not Found: The server can't find the requested resource.
   */
  NOT_FOUND(404, "Not Found"),

  /**
   * 409 Conflict: The request could not be completed due to a conflict with the current state of the target resource.
   */
  CONFLICT(409, "Conflict"),

  /**
   * 415 Unsupported Media Type: The server does not support the media type transmitted in the request.
   */
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),

  /**
   * 429 Too Many Requests: The user has sent too many requests in a given amount of time.
   */
  TOO_MANY_REQUESTS(429, "Too Many Requests"),

  /**
   * 500 Internal Server Error: The server encountered an unexpected condition that prevented it from fulfilling the request.
   */
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

  /**
   * 501 Not Implemented: The server does not support the functionality required to fulfill the request.
   */
  NOT_IMPLEMENTED(501, "Not Implemented"),

  /**
   * 502 Bad Gateway: The server received an invalid response from an inbound server.
   */
  BAD_GATEWAY(502, "Bad Gateway"),

  /**
   * 503 Service Unavailable: The server is currently unable to handle the request due to a temporary overload or maintenance.
   */
  SERVICE_UNAVAILABLE(503, "Service Unavailable");

  private final int code;
  private final String reason;

  /**
   * Constructor for the HttpStatusCode enum.
   *
   * @param code   The numeric HTTP status code.
   * @param reason The standard reason phrase for the status code.
   */
  HttpStatusCodeEnum(int code, String reason) {
    this.code = code;
    this.reason = reason;
  }

  /**
   * Returns the numeric HTTP status code.
   *
   * @return the status code as an integer.
   */
  public int getCode() {
    return code;
  }

  /**
   * Returns the reason phrase associated with the status code.
   *
   * @return the reason phrase as a String.
   */
  public String getReason() {
    return reason;
  }

  /**
   * Returns the HttpStatusCode enum constant for a given numeric code.
   *
   * @param code the numeric HTTP status code.
   * @return the corresponding HttpStatusCode enum constant.
   * @throws IllegalArgumentException if the code is not found.
   */
  public static HttpStatusCodeEnum fromCode(int code) {
    for (HttpStatusCodeEnum status : values()) {
      if (status.code == code) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown HTTP status code: " + code);
  }
}
