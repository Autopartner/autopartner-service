package com.autopartner.domain;

import com.autopartner.api.dto.request.CarRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Year;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
@FieldDefaults(level = PRIVATE)
@Builder
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_seq")
  @SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "client_id")
  @ManyToOne
  Client client;

  @JoinColumn(name = "car_model_id")
  @ManyToOne
  CarModel carModel;

  @Column
  Long companyId;

  @Column
  String plateNumber;

  @Column
  String vinCode;

  @Column
  String note;

  @Column
  Year manufactureYear;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Car create(CarRequest request, Client client, CarModel carModel, Long companyId) {
    return Car.builder()
        .companyId(companyId)
        .plateNumber(request.getPlateNumber())
        .vinCode(request.getVinCode())
        .note(request.getNote())
        .manufactureYear(request.getManufactureYear())
        .client(client)
        .carModel(carModel)
        .build();
  }

  public void update(CarRequest request, Client client, CarModel carModel) {
    plateNumber = request.getPlateNumber();
    vinCode = request.getVinCode();
    note = request.getNote();
    manufactureYear = request.getManufactureYear();
    this.client = client;
    this.carModel = carModel;
  }

  public void delete() {
    active = false;
  }

}
