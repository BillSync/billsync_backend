package com.BillSyncOrg.BillSync.exceptions.clientExceptions.expenseExceptions;

import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;

/**
 * Custom exception thrown when failed to validate an itemized expense.
 */
public class ItemizedExpenseValidationException extends BillSyncClientException {

  /**
   * {@inheritDoc}
   */
  public ItemizedExpenseValidationException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, httpStatusCodeEnum);
  }

  /**
   * {@inheritDoc}
   */
  public ItemizedExpenseValidationException(String message, Exception e,
                                            HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e, httpStatusCodeEnum);
  }
}
