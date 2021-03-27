package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cars")
@FieldDefaults(level = PRIVATE)
public class Car {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_seq")
  @SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "client_id")
  @ManyToOne
  Client client;

  @JoinColumn(name = "car_model_id")
  @ManyToOne
  CarModel carModel;

  @Column(name = "reg_number")
  String regNumber;

  @Column(name = "vin_code")
  String vinCode;

  @Column(name = "notes")
  String notes;

  @Column(name = "mileage")
  Double mileage;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @Column(name = "active")
  Boolean active;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

}
