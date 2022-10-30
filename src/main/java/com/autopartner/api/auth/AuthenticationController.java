package com.autopartner.api.auth;

import com.autopartner.api.dto.request.AuthenticationRequest;
import com.autopartner.api.dto.response.AuthenticationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

  AuthenticationManager authenticationManager;
  JwtVerifier tokenService;

  @PostMapping
  public AuthenticationResponse auth(
      @RequestBody @Valid AuthenticationRequest authenticationRequest) throws AuthenticationException {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authenticationRequest.getEmail(),
            authenticationRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = tokenService.generateToken(authenticationRequest.getEmail());
    return new AuthenticationResponse(token);

  }

}
