package com.BillSyncOrg.BillSync.util.jwt;

import com.BillSyncOrg.BillSync.exceptions.serverExceptions.JWTException;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import io.jsonwebtoken.Claims;
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
   * @throws JWTException the exception while generating the jwt
   */
  public String generateToken(String userId) throws JWTException {
    try {
      return Jwts.builder()
        .setSubject(userId)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // 30 days
        .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
        .compact();
    } catch (Exception e) {
      throw new JWTException("Error generating jwt!", e, HttpStatusCodeEnum.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Extracts all claims (payload) from the JWT token.
   *
   * @param token the JWT token string.
   * @return the extracted claims.
   */
  public Claims extractAllClaims(String token) {
    return Jwts.parser()
      .setSigningKey(secretKey.getBytes())
      .parseClaimsJws(token)
      .getBody();
  }

  /**
   * Extracts the userId embedded in the JWT token.
   *
   * @param token the JWT token string.
   * @return the userId if present.
   */
  public String extractUserId(String token) {
    try {
      return extractAllClaims(token).getSubject();
    } catch (Exception e) {
      return null;
    }
  }
}
