package com.autopartner.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materials")
@FieldDefaults(level = PRIVATE)
public class Material {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materials_seq")
  @SequenceGenerator(name = "materials_seq", sequenceName = "materials_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "material_type_id")
  @ManyToOne
  MaterialType materialType;

  @Column(name = "name")
  String name;

  @Column(name = "vendorCode")
  String vendorCode;

  @Column(name = "originalCode")
  String originalCode;

  @Column(name = "manfacturer")
  String manufacturer;

  @Column(name = "count")
  long count;

  @Column(name = "in_price")
  BigDecimal inPrice;

  @Column(name = "out_price")
  BigDecimal outPrice;

  @Column(name = "notes")
  String notes;

  @Column(name = "active")
  Boolean active;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

}
