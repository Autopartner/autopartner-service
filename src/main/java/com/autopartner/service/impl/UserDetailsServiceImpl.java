package com.autopartner.service.impl;


import static lombok.AccessLevel.PRIVATE;

import com.autopartner.domain.User;
import com.autopartner.model.factory.AppUserFactory;
import com.autopartner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.userRepository.findOneByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException(
          String.format("No user found with username '%s'.", username));
    } else {
      return AppUserFactory.create(user);
    }
  }

}
