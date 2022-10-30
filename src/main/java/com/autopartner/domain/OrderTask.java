package com.autopartner.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_tasks")
@FieldDefaults(level = PRIVATE)
public class OrderTask {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_tasks_seq")
  @SequenceGenerator(name = "order_tasks_seq", sequenceName = "order_tasks_seq", allocationSize = 1)
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
  Double price;

  @Column(name = "discount")
  Double discount;

  @Column(name = "note")
  String note;

  @Column(name = "active")
  Boolean active;

}
