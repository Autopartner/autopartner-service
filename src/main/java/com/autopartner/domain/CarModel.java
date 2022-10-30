package com.autopartner.domain;

import com.autopartner.api.dto.request.CarModelRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_models")
@FieldDefaults(level = PRIVATE)
@Builder
public class CarModel {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_models_seq")
  @SequenceGenerator(name = "car_models_seq", sequenceName = "car_models_seq", allocationSize = 1)
  Long id;

  @Column
  Long companyId;

  @Column
  String name;

  @JoinColumn(name = "car_brand_id")
  @ManyToOne
  CarBrand carBrand;

  @JoinColumn(name = "car_type_id")
  @ManyToOne
  CarType carType;

  @ToString.Exclude
  @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy("id asc")
  @JsonIgnore
  List<Car> cars;

  @Column
  @Builder.Default
  Boolean active = true;

  public static CarModel create(CarModelRequest request, CarBrand brand, CarType type, Long companyId) {
    return CarModel.builder()
        .companyId(companyId)
        .name(request.getName())
        .carBrand(brand)
        .carType(type)
        .build();
  }

  public void update(CarModelRequest request, CarBrand brand, CarType type) {
    name = request.getName();
    carBrand = brand;
    carType = type;
  }

  public void delete() {
    active = false;
  }

}
