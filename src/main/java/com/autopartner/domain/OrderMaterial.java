package com.autopartner.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_materials")
@FieldDefaults(level = PRIVATE)
public class OrderMaterial {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_materials_seq")
  @SequenceGenerator(name = "order_materials_seq", sequenceName = "order_materials_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "order_id")
  @ManyToOne
  Order order;

  @JoinColumn(name = "material_id")
  @ManyToOne
  Material material;

  @Column(name = "count")
  Integer count;

  @Column(name = "price")
  BigDecimal price;

  @Column(name = "note")
  String note;

  @Column(name = "active")
  Boolean active;

}
