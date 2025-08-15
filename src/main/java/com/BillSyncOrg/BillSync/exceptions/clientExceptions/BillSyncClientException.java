package com.BillSyncOrg.BillSync.exceptions.clientExceptions;

import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;

public class BillSyncClientException extends Exception{

  private final HttpStatusCodeEnum httpStatusCodeEnum;

  public BillSyncClientException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message);
    this.httpStatusCodeEnum = httpStatusCodeEnum;
  }

  public BillSyncClientException(String message, Exception e, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e);
    this.httpStatusCodeEnum = httpStatusCodeEnum;
  }

  public HttpStatusCodeEnum getHttpStatusCode() {
    return httpStatusCodeEnum;
  }
}
