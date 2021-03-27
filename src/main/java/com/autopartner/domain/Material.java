package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
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
