package autopartner.model.factory;

import autopartner.domain.entity.User;
import autopartner.model.security.AppUser;
import org.springframework.security.core.authority.AuthorityUtils;

public class AppUserFactory {

  public static AppUser create(User user) {
    return new AppUser(
      user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getEmail(),
      user.getLastPasswordReset(),
      AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
    );
  }

}
