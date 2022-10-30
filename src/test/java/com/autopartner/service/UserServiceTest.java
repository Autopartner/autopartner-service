package com.autopartner.service;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;
import com.autopartner.api.dto.request.UserRequest;
import com.autopartner.api.dto.request.UserRequestFixture;
import com.autopartner.domain.User;
import com.autopartner.domain.UserFixture;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.autopartner.api.dto.request.CompanyRegistrationRequestFixture.createCompanyRegistrationRequestWithoutPassword;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  UserServiceImpl userService;
  @Captor
  ArgumentCaptor<User> userArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> userIdArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> companyIdArgumentCaptor;
  @Mock
  private UserRepository userRepository;

  @Test
  void findAll() {
    Long companyId = 1L;
    User user = UserFixture.createUser();
    List<User> users = List.of(user, new User());
    when(userRepository.findAll(companyId)).thenReturn(users);
    Iterable<User> userIterable = userService.findAll(companyId);
    List<User> userList = StreamSupport.stream(userIterable.spliterator(), false).toList();
    assertThat(userList.size()).isEqualTo(users.size());
    assertThat(userList.stream().findFirst().get()).isEqualTo(user);
  }

  @Test
  void findById() {
    Long companyId = 1L;
    User user = UserFixture.createUser();
    when(userRepository.findById(anyLong(), anyLong())).thenReturn(Optional.ofNullable(user));
    userService.findById(user.getId(), companyId);
    verify(userRepository).findById(userIdArgumentCaptor.capture(), companyIdArgumentCaptor.capture());
    long id = userIdArgumentCaptor.getValue();
    long currentCompanyId = companyIdArgumentCaptor.getValue();
    assertThat(id).isEqualTo(user.getId());
    assertThat(currentCompanyId).isEqualTo(companyId);
  }

  @Test
  void saveUser() {
    User user = UserFixture.createUser();
    CompanyRegistrationRequest companyRegistrationRequest = createCompanyRegistrationRequestWithoutPassword();
    userService.save(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    assertThat(user.getFirstName()).isEqualTo(companyRegistrationRequest.getFirstName());
    assertThat(user.getLastName()).isEqualTo(companyRegistrationRequest.getLastName());
    assertThat(user.getEmail()).isEqualTo(companyRegistrationRequest.getEmail());
  }

  @Test
  void update() {
    User user = UserFixture.createUser();
    UserRequest request = UserRequestFixture.createUserRequest();
    userService.update(user, request);
    verify(userRepository).save(userArgumentCaptor.capture());
    User actualUser = userArgumentCaptor.getValue();
    assertThat(actualUser.getEmail()).isEqualTo((request.getEmail()));
  }

  @Test
  void deleteUser() {
    User user = UserFixture.createUser();
    userService.delete(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    User actualUser = userArgumentCaptor.getValue();
    assertThat(actualUser).isEqualTo(user);
    assertThat(actualUser.getActive()).isFalse();
  }
}
