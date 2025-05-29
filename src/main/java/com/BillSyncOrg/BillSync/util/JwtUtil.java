package com.BillSyncOrg.BillSync.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for creating and managing JSON Web Tokens (JWTs).
 * <p>
 * This class generates signed JWT tokens that embed the user ID, with a long expiration
 * (30 days). These tokens are returned during login and stored in MongoDB.
 * </p>
 */
@Component
public class JwtUtil {

  /**
   * The secret key used to sign JWTs. This should be a strong, long, and confidential value.
   * Injected from the {@code application.properties} file.
   */
  @Value("${jwt.secret}")
  private String secretKey;

  /**
   * Generates a signed JWT containing the user ID as the subject.
   *
   * @param userId the MongoDB user ID to embed in the token
   * @return a JWT string signed with the configured secret key
   */
  public String generateToken(String userId) {
    return Jwts.builder()
      .setSubject(userId)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // 30 days
      .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
      .compact();
  }
}
