package com.BillSyncOrg.BillSync.exceptions.serverExceptions;

import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;

/**
 * Custom exception class thrown during jwt failures.
 * <p>
 * This exception is used to indicate specific issues such as duplicate
 * email or phone number, instead of using generic {@link BillSyncServerException}.
 * </p>
 */
public class JWTException extends BillSyncServerException{

  /**
   * Constructs a new JWTException with a specified message.
   *
   * @param message the detail message explaining the reason for the exception
   * @param httpStatusCodeEnum the http status code associated with the error
   */
  public JWTException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, httpStatusCodeEnum);
  }

  /**
   * Constructs a new JWTException with a specified message and error stack trace.
   *
   * @param message the detail message explaining the reason for the exception
   * @param e the detailed exception stack trace
   * @param httpStatusCodeEnum the http status code associated with the error
   */
  public JWTException(String message, Exception e, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e, httpStatusCodeEnum);
  }
}
