package com.autopartner.api.configuration;

import com.autopartner.api.auth.JwtRequestFilter;
import com.autopartner.api.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfiguration extends WebSecurityConfigurerAdapter {

  public static final ErrorResponse UNAUTHORIZED_RESPONSE = new ErrorResponse(SC_UNAUTHORIZED, SC_UNAUTHORIZED, "Unauthorized");
  UserDetailsService userDetailsService;

  JwtRequestFilter jwtRequestFilter;

  ObjectMapper objectMapper;

  @Autowired
  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .httpBasic().disable()
        .cors().and().csrf().disable();

    httpSecurity
        .exceptionHandling()
        .authenticationEntryPoint((request, response, authException) -> writeUnauthorized(response))
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/api/v1/auth").permitAll()
        .antMatchers(HttpMethod.POST, "/api/v1/companies").permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/api/v1/**").permitAll()
        .antMatchers("/api/v1/**").authenticated();

    httpSecurity
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  private void writeUnauthorized(HttpServletResponse res) throws IOException {
    res.setContentType("application/json;charset=UTF-8");
    res.setStatus(SC_UNAUTHORIZED);
    res.getWriter().write(objectMapper.writeValueAsString(UNAUTHORIZED_RESPONSE));
  }

}
