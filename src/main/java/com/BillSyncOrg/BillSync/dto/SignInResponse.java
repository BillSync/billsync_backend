package com.BillSyncOrg.BillSync.dto;

/**
 * DTO representing the response returned after a successful login.
 * <p>
 * Contains a JSON Web Token (JWT) which is used by the client to authenticate
 * subsequent API requests by including it in the {@code Authorization} header.
 * </p>
 */
public class SignInResponse {

  /**
   * The JWT token issued to the authenticated user.
   */
  private String token;

  public SignInResponse(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
