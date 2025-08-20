package com.BillSyncOrg.BillSync.exceptions.clientExceptions.groupExceptions;

import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;

/**
 * Custom exception thrown when the group name is not unique or given group name already exists.
 */
public class GroupNameNotUniqueException extends BillSyncClientException {

  /**
   * {@inheritDoc}
   */
  public GroupNameNotUniqueException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, httpStatusCodeEnum);
  }

  /**
   * {@inheritDoc}
   */
  public GroupNameNotUniqueException(String message, Exception e,
                                     HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e, httpStatusCodeEnum);
  }
}
