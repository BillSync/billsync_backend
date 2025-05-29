package com.BillSyncOrg.BillSync.exceptions;

/**
 * Custom exception class thrown during user login failures.
 * <p>
 * This exception is used to indicate specific issues such as duplicate
 * email or phone number, instead of using generic {@link BillSyncException}.
 * </p>
 */
public class UserSignInException extends  BillSyncException{
  /**
   * Constructs a new UserSignInException with a specified message.
   *
   * @param message the detail message explaining the reason for the exception
   */
  public UserSignInException(String message) {
    super(message);
  }

  /**
   * Constructs a new UserSignInException with a specified message and error stack trace.
   *
   * @param message the detail message explaining the reason for the exception
   * @param e the detailed exception stack trace
   */
  public UserSignInException(String message, Exception e) {
    super(message, e);
  }
}
