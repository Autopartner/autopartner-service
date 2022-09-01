package com.autopartner.domain;

import com.autopartner.api.dto.request.CarBrandRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_brands")
@FieldDefaults(level = PRIVATE)
@Builder
public class CarBrand {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_brands_seq")
  @SequenceGenerator(name = "car_brands_seq", sequenceName = "car_brands_seq", allocationSize = 1)
  Long id;

  @Column
  Long companyId;

  @Column
  String name;

  @OneToMany(mappedBy = "carBrand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy("id asc")
  @JsonIgnore
  List<CarModel> models;

  @Column
  @Builder.Default
  Boolean active = true;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  public static CarBrand create(CarBrandRequest request, Long companyId) {
    return CarBrand.builder()
        .companyId(companyId)
        .name(request.getName())
        .build();
  }

  public void update(CarBrandRequest request) {
    name = request.getName();
  }

  public void delete() {
    active = false;
  }

}
