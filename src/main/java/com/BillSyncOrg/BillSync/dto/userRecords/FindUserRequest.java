package com.BillSyncOrg.BillSync.dto.userRecords;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for accepting a search request to find a user
 * by email or phone number.
 * <p>
 * This DTO is used in the {@code /find-user} POST endpoint to encapsulate the
 * search input provided by the client. The input is validated to ensure that
 * a non-blank value is supplied.
 * </p>
 *
 * <p><b>Example JSON Request:</b></p>
 * <pre>
 * {
 *   "searchValue": "alice@example.com"
 * }
 * </pre>
 *
 * <p><b>Validation:</b></p>
 * <ul>
 *     <li>{@code @NotBlank} ensures that the searchValue field is not null,
 *     empty, or only whitespace.</li>
 * </ul>
 *
 * @author YourName
 */
public class FindUserRequest {

  /**
   * The value to search for, either the user's email or phone number.
   */
  @NotBlank(message = "Search value is required")
  private String searchValue;

  /**
   * Returns the search value provided by the client.
   *
   * @return the search value (email or phone number)
   */
  public String getSearchValue() {
    return searchValue;
  }

  /**
   * Sets the search value.
   *
   * @param searchValue the email or phone number to search for
   */
  public void setSearchValue(String searchValue) {
    this.searchValue = searchValue;
  }
}
