package com.autopartner.model.factory;

import com.autopartner.domain.User;
import com.autopartner.model.security.AppUser;
import org.springframework.security.core.authority.AuthorityUtils;

public class AppUserFactory {

  public static AppUser create(User user) {
    return new AppUser(
        user.getId(),
        user.getEmail(),
        user.getPassword(),
        user.getEmail(),
        user.getLastPasswordReset(),
        AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
    );
  }

}
