package com.autopartner.api.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class JwtVerifier {

  private static final Integer EXPIRATION_HOURS = 1;
  private static final String ISSUER = "auto=partner-service";
  @Value("${autopartner.token.secret}")
  private String secret;

  public String generateToken(String subject) {
    final Date expDate = Date.from(LocalDateTime.now().plusHours(EXPIRATION_HOURS)
        .atZone(ZoneId.systemDefault()).toInstant());
    return JWT.create()
        .withIssuer(ISSUER)
        .withSubject(subject)
        .withExpiresAt(expDate)
        .sign(Algorithm.HMAC256(secret));
  }

  public String verify(DecodedJWT jwt) {
    if (ISSUER.equals(jwt.getIssuer())) {
      final DecodedJWT verified = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
      return verified.getSubject();
    }
    return null;
  }

}
