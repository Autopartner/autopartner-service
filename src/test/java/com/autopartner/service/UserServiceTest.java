package com.autopartner.service;

import com.autopartner.api.dto.CompanyRegistrationRequest;
import com.autopartner.api.dto.CompanyRegistrationRequestFixture;
import com.autopartner.domain.User;
import com.autopartner.domain.UserFixture;
import com.autopartner.exception.NotActiveException;
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
    companyRegistrationRequest = CompanyRegistrationRequestFixture.createCompanyRegistrationRequest();
  }

  @Test
  void listAllUsers() {
    when(userRepository.findByActiveTrue()).thenReturn(users);
    Iterable<User> userIterable = userService.listAllUsers();
    List<User> userList = StreamSupport.stream(userIterable.spliterator(), false).toList();
    assertThat(userList.size()).isEqualTo(users.size());
    assertThat(userList.stream().findFirst().get()).isEqualTo(user);
  }

  @Test
  void getUserById_whenValidId() {
    when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(user);
    userService.getUserById(user.getId());
    verify(userRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    id = longArgumentCaptor.getValue();
    assertThat(id).isEqualTo(user.getId());
  }

  @Test
  void saveUser() {
    userService.saveUser(user);
    verify(userRepository).save(userArgumentCaptor.capture());
    assertThat(user.getFirstName()).isEqualTo(companyRegistrationRequest.getFirstName());
    assertThat(user.getLastName()).isEqualTo(companyRegistrationRequest.getLastName());
    assertThat(user.getEmail()).isEqualTo(companyRegistrationRequest.getEmail());
  }

  @Test
  void deleteUser() {
    when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(user);
    userService.deleteUser(user.getId());
    verify(userRepository).findByIdAndActiveTrue(longArgumentCaptor.capture());
    Long userId = longArgumentCaptor.getValue();
    assertThat(user.getId()).isEqualTo(userId);
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
    userService.saveUser(user1);
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
    userService.saveUser(user1);
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
    userService.getUserByEmail(user.getEmail());
    verify(userRepository).findOneByEmail(stringArgumentCaptor.capture());
    String username = stringArgumentCaptor.getValue();
    assertThat(username).isEqualTo(user.getEmail());
  }

  @Test
  void shouldThrowNotActiveException_whenFindUserByIdIsNotActive() {
    user.setActive(false);
    when(userRepository.findByIdAndActiveTrue(anyLong())).thenReturn(null);
    assertThrows(NotActiveException.class, () -> userService.getUserById(user.getId()));
  }

  @Test
  void shouldThrowNotActiveException_whenUserIdDoesNotExist() {
    when(userRepository.findByIdAndActiveTrue(20L)).thenReturn(null);
    assertThrows(NotActiveException.class, () -> userService.getUserById(20L));
  }
}