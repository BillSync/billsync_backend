package com.BillSyncOrg.BillSync.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Entity representing a JWT token that has been blacklisted and is no longer considered valid.
 * <p>
 * Blacklisting is used to prevent further use of a JWT after logout or manual invalidation.
 */
@Document(collection = "blacklisted_tokens")
public class BlacklistedToken {

  @Id
  private String id;

  /**
   * The JWT token string that has been blacklisted.
   */
  private String token;

  /**
   * The date and time when the token was added to the blacklist.
   */
  private LocalDateTime blacklistedAt;

  public BlacklistedToken() {}

  public BlacklistedToken(String token) {
    this.token = token;
    this.blacklistedAt = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public String getToken() {
    return token;
  }

  public LocalDateTime getBlacklistedAt() {
    return blacklistedAt;
  }
}
