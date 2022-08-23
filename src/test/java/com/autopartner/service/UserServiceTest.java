package com.autopartner.service;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;
import com.autopartner.api.dto.request.CompanyRegistrationRequestFixture;
import com.autopartner.domain.User;
import com.autopartner.domain.UserFixture;
import com.autopartner.repository.UserRepository;
import com.autopartner.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  UserServiceImpl userService;
  @Captor
  ArgumentCaptor<User> userArgumentCaptor;
  @Captor
  ArgumentCaptor<Long> longArgumentCaptor;
  @Captor
  ArgumentCaptor<String> stringArgumentCaptor;
  CompanyRegistrationRequest companyRegistrationRequest;
  List<User> users;
  User user;
  long id;

  @BeforeEach
  public void init() {
    user = UserFixture.createUser();
    users = List.of(user, new User());
    companyRegistrationRequest = CompanyRegistrationRequestFixture.createCompanyRegistrationRequestWithoutPassword();
  }

  @Test
  void listAllUsers() {
    when(userRepository.findAllByActiveTrue()).thenReturn(users);
    Iterable<User> userIterable = userService.findAll();
    List<User> userList = StreamSupport.stream(userIterable.spliterator(), false).toList();
    assertThat(userList.size()).isEqualTo(users.size());
    assertThat(userList.stream().findFirst().get()).isEqualTo(user);
  }

  @Test
  void getUserById_whenValidId() {
    when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(Optional.ofNullable(user));
    userService.findById(user.getId());
    verify(userRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(user.getId());
  }

  @Test
  void saveUser() {
    userService.save(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    assertThat(user.getFirstName()).isEqualTo(companyRegistrationRequest.getFirstName());
    assertThat(user.getLastName()).isEqualTo(companyRegistrationRequest.getLastName());
    assertThat(user.getEmail()).isEqualTo(companyRegistrationRequest.getEmail());
  }

  @Test
  void deleteUser() {
    userService.delete(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    User actualUser = userArgumentCaptor.getValue();
    assertThat(actualUser).isEqualTo(user);
    assertThat(actualUser.getActive()).isFalse();
  }

  @Test
  void isUnique_whenTheSameUser_thanReturnsTrue() {
    when(userRepository.findOneByEmail(anyString())).thenReturn(user);
    boolean result = userService.isEmailUnique(user);
    verify(userRepository).findOneByEmail(stringArgumentCaptor.capture());
    String email = stringArgumentCaptor.getValue();
    assertThat(email).isEqualTo(user.getEmail());
    assertThat(result).isEqualTo(true);
  }

  @Test
  void isUnique_whenEmailIsUnique_thanReturnsTrue() {
    User user1 = UserFixture.createUser();
    user1.setEmail("userTest@gmail.com");
    user1.setId(2L);
    userService.save(user1);
    when(userRepository.findOneByEmail(anyString())).thenReturn(user1);
    boolean result = userService.isEmailUnique(user1);
    verify(userRepository).findOneByEmail(stringArgumentCaptor.capture());
    String email = stringArgumentCaptor.getValue();
    assertThat(email).isEqualTo(user1.getEmail());
    assertThat(result).isEqualTo(true);
  }

  @Test
  void isUnique_whenEmailIsNotUnique_thanReturnsFalse() {
    User user1 = UserFixture.createUser();
    user1.setId(4L);
    userService.save(user1);
    when(userRepository.findOneByEmail(anyString())).thenReturn(user);
    boolean result = userService.isEmailUnique(user1);
    verify(userRepository).findOneByEmail(stringArgumentCaptor.capture());
    String username = stringArgumentCaptor.getValue();
    assertThat(username).isEqualTo(user1.getEmail());
    assertThat(result).isEqualTo(false);
  }

  @Test
  void getUserByUsername() {
    when(userRepository.findOneByEmail(anyString())).thenReturn(user);
    userService.findByEmail(user.getEmail());
    verify(userRepository).findOneByEmail(stringArgumentCaptor.capture());
    String username = stringArgumentCaptor.getValue();
    assertThat(username).isEqualTo(user.getEmail());
  }
}