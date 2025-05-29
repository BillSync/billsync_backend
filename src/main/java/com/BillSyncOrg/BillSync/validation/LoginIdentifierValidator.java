package com.BillSyncOrg.BillSync.validation;

import com.BillSyncOrg.BillSync.dto.SignInRequest;
import com.BillSyncOrg.BillSync.util.ValidLoginIdentifier;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator class for {@link ValidLoginIdentifier}.
 * <p>
 * Ensures that exactly one of the {@code email} or {@code phoneNumber} fields
 * in a {@link SignInRequest} is populated (i.e., XOR logic).
 * </p>
 */
public class LoginIdentifierValidator implements ConstraintValidator<ValidLoginIdentifier, SignInRequest> {

  @Override
  public boolean isValid(SignInRequest request, ConstraintValidatorContext context) {
    String email = request.getEmail();
    String phone = request.getPhoneNumber();
    String password = request.getPassword();

    boolean hasEmail = email != null && !email.isBlank();
    boolean hasPhone = phone != null && !phone.isBlank();
    boolean hasPassword = password != null && !password.isBlank();

    // Validate: only one of email or phone, and password must be present
    if (hasEmail == hasPhone) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Either email or phone must be provided, not both.")
        .addConstraintViolation();
      return false;
    }

    if (!hasPassword) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Password must not be blank.")
        .addConstraintViolation();
      return false;
    }

    return true;
  }
}
