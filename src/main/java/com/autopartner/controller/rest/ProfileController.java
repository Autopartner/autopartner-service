package com.autopartner.controller.rest;

import com.autopartner.model.security.AppUser;
import com.autopartner.security.TokenUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  @Value("${autopartner.token.header}")
  private String tokenHeader;

  @Autowired
  private TokenUtils tokenUtils;

  @Autowired
  private UserDetailsService userDetailsService;

  @RequestMapping(path = "${autopartner.route.profile}", method = RequestMethod.GET)
  @PreAuthorize("@securityService.hasProtectedAccess()")
  public ResponseEntity<?> getProfile(HttpServletRequest request) {
    String token = request.getHeader(this.tokenHeader);
    String username = this.tokenUtils.getUsernameFromToken(token);
    AppUser user = (AppUser) this.userDetailsService.loadUserByUsername(username);
    return ResponseEntity.ok(user);
  }

}
