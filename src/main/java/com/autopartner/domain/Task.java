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
@Table(name = "tasks")
@FieldDefaults(level = PRIVATE)
public class Task {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
  @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "task_category_id")
  @ManyToOne
  TaskCategory taskCategory;

  @Column
  String name;

  @Column
  Boolean active;

}
