package uk.gov.beis.els.mvc;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class HttpHeaderFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {

    // Disable caching
    response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.addHeader("pragma", "no-cache");

    // CSP
    response.addHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' www.googletagmanager.com; connect-src 'self' www.googletagmanager.com *.google-analytics.com; img-src 'self' www.google-analytics.com; object-src 'none'");

    // Force browser XSS filter
    response.addHeader("X-XSS-Protection", "1; mode=block");

    // Force strict MIME checking
    response.addHeader("X-Content-Type-Options", "nosniff");

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    if (request.getRequestURI().startsWith("/assets/")) {
      return true;
    }

    // Do not add CSP etc. to Swagger UI
    return "/swagger-ui/index.html".equals(request.getRequestURI());
  }

}
