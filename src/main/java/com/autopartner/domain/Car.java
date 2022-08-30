package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

import com.autopartner.api.dto.request.CarRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
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
  LocalDate manufactureYear;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @Column
  @Builder.Default
  Boolean active = true;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public static Car create(CarRequest request, Long companyId) {
    return Car.builder()
            .companyId(companyId)
            .plateNumber(request.getPlateNumber())
            .vinCode(request.getVinCode())
            .note(request.getNote())
            .manufactureYear(LocalDate.parse(request.getManufactureYear()))
            .build();
  }

  public void update(CarRequest request) {
    plateNumber = request.getPlateNumber();
    vinCode = request.getVinCode();
    note = request.getNote();
    manufactureYear = LocalDate.parse(request.getManufactureYear());
  }

  public void delete() {
    active = false;
  }

}
