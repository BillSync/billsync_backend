package com.BillSyncOrg.BillSync.util.annotations;

import com.BillSyncOrg.BillSync.dto.userAuthentication.SignInRequest;
import com.BillSyncOrg.BillSync.validation.LoginIdentifierValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation to ensure that exactly one of
 * {@code email} or {@code phoneNumber} is present in a {@link SignInRequest}.
 * <p>
 * This constraint applies to the class level of the {@code LoginRequest} DTO.
 * </p>
 */
@Documented
@Constraint(validatedBy = LoginIdentifierValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLoginIdentifier {

  /**
   * The default error message when validation fails.
   */
  String message() default "Either email or phone number must be provided, but not both.";

  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
