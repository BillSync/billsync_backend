package com.BillSyncOrg.BillSync.util.jwt;

import com.BillSyncOrg.BillSync.context.RequestContext;
import com.BillSyncOrg.BillSync.repository.BlacklistedTokenRepository;
import com.BillSyncOrg.BillSync.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * This filter intercepts all incoming HTTP requests and validates the JWT token if provided
 * in the "Authorization" header. If the token is valid, it extracts and stores the user ID
 * into the RequestContext for use in downstream layers.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;
  private final BlacklistedTokenRepository blacklistRepo;

  @Autowired
  public JwtAuthenticationFilter(JwtUtil jwtUtil,
                                 UserRepository userRepository,
                                 BlacklistedTokenRepository blacklistRepo) {
    this.jwtUtil = jwtUtil;
    this.userRepository = userRepository;
    this.blacklistRepo = blacklistRepo;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
    throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);

        if (blacklistRepo.existsByToken(token)) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Invalid token");
          return;
        }

        String userId = jwtUtil.extractUserId(token);

        if (userId == null || !userRepository.existsById(userId)) {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().write("Invalid token");
          return;
        }

        RequestContext.setUserId(userId);

        UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }

      filterChain.doFilter(request, response);

    } finally {
      RequestContext.clear(); // Ensure no memory leaks
    }
  }
}
