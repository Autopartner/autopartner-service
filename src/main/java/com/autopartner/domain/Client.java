package com.autopartner.domain;

import com.autopartner.api.dto.request.ClientRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
@FieldDefaults(level = PRIVATE)
@Builder
public class Client {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
  @SequenceGenerator(name = "clients_seq", sequenceName = "clients_seq", allocationSize = 1)
  Long id;

  @Column
  Long companyId;

  @Column
  Long userId;

  @Column
  String firstName;

  @Column
  String lastName;

  @Column
  String companyName;

  @Column
  String address;

  @Column
  String phone;

  @Column
  String email;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @Column
  int productDiscount;

  @Column
  int taskDiscount;

  @Column
  ClientType clientType;

  @Column
  String note;

  @ToString.Exclude
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy("id asc")
  @JsonIgnore
  List<Car> cars;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Client create(ClientRequest request, Long companyId) {
    return Client.builder()
        .companyId(companyId)
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .companyName(request.getCompanyName())
        .address(request.getAddress())
        .phone(request.getPhone())
        .email(request.getEmail())
        .productDiscount(request.getProductDiscount())
        .taskDiscount(request.getTaskDiscount())
        .clientType(request.getClientType())
        .note(request.getNote())
        .build();
  }

  public void update(ClientRequest request) {
    firstName = request.getFirstName();
    lastName = request.getLastName();
    companyName = request.getCompanyName();
    address = request.getAddress();
    phone = request.getPhone();
    email = request.getEmail();
    productDiscount = request.getProductDiscount();
    taskDiscount = request.getTaskDiscount();
    clientType = request.getClientType();
    note = request.getNote();
  }

  public void delete() {
    active = false;
  }
}
