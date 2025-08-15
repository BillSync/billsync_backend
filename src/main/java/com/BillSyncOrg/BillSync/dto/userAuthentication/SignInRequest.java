package com.BillSyncOrg.BillSync.dto.userAuthentication;

import com.BillSyncOrg.BillSync.util.ValidLoginIdentifier;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO representing a user login request.
 * <p>
 * The client must provide exactly one of the two fields: {@code email} or {@code phoneNumber},
 * along with a {@code password}. The request will be validated using a custom constraint
 * {@link ValidLoginIdentifier} to ensure this rule is followed.
 * </p>
 *
 * <p>This class is used by the controller to accept and validate user input during login.</p>
 */
@ValidLoginIdentifier
public class SignInRequest {

  /**
   * The email address provided by the user for login.
   * Optional, but either this or phoneNumber must be provided.
   */
  private String email;

  /**
   * The phone number provided by the user for login.
   * Optional, but either this or email must be provided.
   */
  private String phoneNumber;

  /**
   * The password submitted by the user for authentication.
   * This field is mandatory.
   */
  @NotBlank(message = "Password is required")
  private String password;

  // Getters and setters

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
