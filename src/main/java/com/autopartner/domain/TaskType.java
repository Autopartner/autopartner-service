package com.autopartner.domain;


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
@Table(name = "task_types")
@FieldDefaults(level = PRIVATE)
public class TaskType {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_types_seq")
  @SequenceGenerator(name = "task_types_seq", sequenceName = "task_types_seq", allocationSize = 1)
  Long id;

  @Column(name = "name")
  String name;

  @OneToMany(mappedBy = "taskType", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<Task> tasks;

  @Column(name = "active")
  Boolean active;

}
