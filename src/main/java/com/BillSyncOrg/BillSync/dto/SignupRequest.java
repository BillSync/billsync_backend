package com.BillSyncOrg.BillSync.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for user sign-up requests.
 * <p>
 * This class encapsulates the input data required for a new user registration.
 * It includes validations to ensure that the provided email, phone number, and password
 * meet predefined constraints before the request is processed.
 * </p>
 *
 * <p>
 * Validation Annotations:
 * <ul>
 *     <li>{@code @NotBlank} ensures the field is not null or empty.</li>
 *     <li>{@code @Email} ensures the email field follows a valid email format.</li>
 *     <li>{@code @Pattern} validates the phone number contains exactly 10 digits.</li>
 *     <li>{@code @Size} ensures password is at least 6 characters long.</li>
 * </ul>
 * </p>
 *
 * @author
 */
public class SignupRequest {

  /**
   * User's email address.
   * Must be a valid email format and not empty.
   */
  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  /**
   * User's 10-digit phone number.
   * Must contain only digits and not be empty.
   */
  @NotBlank(message = "Phone number is required")
  @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
  private String phoneNumber;

  /**
   * User's password.
   * Must be at least 6 characters long and not empty.
   */
  @NotBlank(message = "Password is required")
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

  // Getters and Setters

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
