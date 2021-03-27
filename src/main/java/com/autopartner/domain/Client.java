package com.autopartner.domain;


import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clients")
@FieldDefaults(level = PRIVATE)
public class Client {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clients_seq")
  @SequenceGenerator(name = "clients_seq", sequenceName = "clients_seq", allocationSize = 1)
  Long id;

  @Column(name = "first_name")
  String firstName;

  @Column(name = "last_name")
  String lastName;

  @Column(name = "company_name")
  String companyName;

  @Column(name = "address")
  String address;

  @Column(name = "phone")
  String phone;

  @Column(name = "email")
  String email;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @Column(name = "note")
  String note;

  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<Car> cars;

  @Column(name = "active")
  Boolean active;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

}
