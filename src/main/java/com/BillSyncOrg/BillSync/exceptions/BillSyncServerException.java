package com.BillSyncOrg.BillSync.exceptions;

import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;

public class BillSyncServerException extends Exception{

  private final HttpStatusCodeEnum httpStatusCodeEnum;

  public BillSyncServerException(String message, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message);
    this.httpStatusCodeEnum = httpStatusCodeEnum;
  }

  public BillSyncServerException(String message, Exception e, HttpStatusCodeEnum httpStatusCodeEnum) {
    super(message, e);
    this.httpStatusCodeEnum = httpStatusCodeEnum;
  }

  public HttpStatusCodeEnum getHttpStatusCode() {
    return httpStatusCodeEnum;
  }
}
