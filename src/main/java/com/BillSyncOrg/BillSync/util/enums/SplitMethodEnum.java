package com.BillSyncOrg.BillSync.util.enums;

/**
 * Enum representing how an expense is split among group members.
 */
public enum SplitMethodEnum {

  EQUAL("equal"),
  ITEMIZED("itemized");

  private final String value;

  SplitMethodEnum(String value) {
    this.value = value;
  }

  public String getValue() { return value; }

}
