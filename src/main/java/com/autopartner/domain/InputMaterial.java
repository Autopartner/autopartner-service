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
