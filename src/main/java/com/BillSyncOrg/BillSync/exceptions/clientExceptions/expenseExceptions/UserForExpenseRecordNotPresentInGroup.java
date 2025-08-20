package com.BillSyncOrg.BillSync.exceptions.clientExceptions.expenseExceptions;

import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;

/**
 * The custom exception if thrown if the user for expense is not present in the group.
 */
public class UserForExpenseRecordNotPresentInGroup extends BillSyncClientException {

  /**
   * {@inheritDoc}
   */
  public UserForExpenseRecordNotPresentInGroup(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, httpStatusCodeEnum);
  }

  /**
   * {@inheritDoc}
   */
  public UserForExpenseRecordNotPresentInGroup(String message, Exception e, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e, httpStatusCodeEnum);
  }
}
