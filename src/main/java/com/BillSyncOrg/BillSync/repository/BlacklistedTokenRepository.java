package com.BillSyncOrg.BillSync.repository;

import com.BillSyncOrg.BillSync.model.BlacklistedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for performing CRUD operations on blacklisted JWT tokens.
 */
public interface BlacklistedTokenRepository extends MongoRepository<BlacklistedToken, String> {

  /**
   * Checks if a token already exists in the blacklist.
   *
   * @param token the JWT token string.
   * @return true if the token is blacklisted, false otherwise.
   */
  boolean existsByToken(String token);
}
