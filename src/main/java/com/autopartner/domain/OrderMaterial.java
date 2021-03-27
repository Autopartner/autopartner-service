package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @JoinColumn(name = "task_id")
  @ManyToOne
  Task task;

  @Column(name = "count")
  Integer count;

  @Column(name = "price")
  BigDecimal price;

  @Column(name = "note")
  String note;

  @Column(name = "active")
  Boolean active;

}
