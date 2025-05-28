package com.BillSyncOrg.BillSync.exceptions;

public class BillSyncException extends Exception{

  public BillSyncException(String message) {
    super(message);
  }

  public BillSyncException(String message, Exception e) {
    super(message, e);
  }
}
