package com.BillSyncOrg.BillSync.exceptions.clientExceptions;

import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;

/**
 * Custom exception class thrown if record is not found in the DB.
 * <p>
 * This exception is used to indicate specific issues such as record not found with given data,
 * instead of using generic {@link BillSyncServerException}.
 * </p>
 */
public class RecordNotFoundException extends BillSyncClientException {

  /**
   * {@inheritDoc}
   */
  public RecordNotFoundException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, httpStatusCodeEnum);
  }

  /**
   * {@inheritDoc}
   */
  public RecordNotFoundException(String message, Exception e,
                                 HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e, httpStatusCodeEnum);
  }
}
