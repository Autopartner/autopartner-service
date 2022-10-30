package com.autopartner.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "input_materials")
@FieldDefaults(level = PRIVATE)
public class InputMaterial {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "input_materials_seq")
  @SequenceGenerator(name = "input_materials_seq", sequenceName = "input_materials_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "material_id")
  @ManyToOne
  Material material;

  @Column(name = "count")
  Integer count;

  @Column(name = "note")
  String note;

  @Column(name = "active")
  Boolean active;

  @Column(name = "created_at")
  LocalDateTime createdAt;

}
