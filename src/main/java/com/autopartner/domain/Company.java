package com.autopartner.domain;

import com.autopartner.api.dto.request.CompanyRegistrationRequest;
import com.autopartner.api.dto.request.CompanyRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "companies")
@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
  @SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 1)
  Long id;

  @Column(name = "company_name")
  String name;

  @Column
  String country;

  @Column
  String city;

  @Column
  @Builder.Default
  Boolean active = true;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime updatedAt;

  public static Company create(CompanyRegistrationRequest request) {
    return Company.builder()
        .name(request.getName())
        .country(request.getCountry())
        .city(request.getCity())
        .build();
  }

  public void update(CompanyRequest request) {
    name = request.getName();
    country = request.getCountry();
    city = request.getCity();
  }

  public void delete() {
    active = false;
  }
}
