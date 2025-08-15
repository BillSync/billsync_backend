package com.BillSyncOrg.BillSync.exceptions.clientExceptions;

import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;

/**
 * Custom exception class thrown during user login failures.
 * <p>
 * This exception is used to indicate specific issues such as duplicate
 * email or phone number, instead of using generic {@link BillSyncClientException}.
 * </p>
 */
public class UserSignInClientException extends BillSyncClientException {
  /**
   * Constructs a new UserSignInException with a specified message.
   *
   * @param message the detail message explaining the reason for the exception
   * @param httpStatusCodeEnum the http status code associated with the error
   */
  public UserSignInClientException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, httpStatusCodeEnum);
  }

  /**
   * Constructs a new UserSignInException with a specified message and error stack trace.
   *
   * @param message the detail message explaining the reason for the exception
   * @param e the detailed exception stack trace
   * @param httpStatusCodeEnum the http status code associated with the error
   */
  public UserSignInClientException(String message, Exception e, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e, httpStatusCodeEnum);
  }
}
