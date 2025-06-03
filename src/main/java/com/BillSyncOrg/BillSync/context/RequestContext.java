package com.BillSyncOrg.BillSync.context;


/**
 * RequestContext is a thread-local storage utility class that holds contextual information
 * for the current HTTP request, such as the user ID extracted from a JWT token.
 * <p>
 * This allows for sharing request-specific user information across multiple layers
 * of the application (e.g., services, filters) without passing it explicitly.
 */
public class RequestContext {

  private static final ThreadLocal<String> userIdHolder = new ThreadLocal<>();

  /**
   * Sets the current request's user ID.
   *
   * @param userId the user ID extracted from the JWT token.
   */
  public static void setUserId(String userId) {
    userIdHolder.set(userId);
  }

  /**
   * Gets the current request's user ID.
   *
   * @return the user ID stored in the current thread, or null if not set.
   */
  public static String getUserId() {
    return userIdHolder.get();
  }

  /**
   * Clears the user ID from the current thread to prevent data leaks or reuse
   * in thread pools.
   */
  public static void clear() {
    userIdHolder.remove();
  }
}
