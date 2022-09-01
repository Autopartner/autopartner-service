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
@Table(name = "material_types")
@FieldDefaults(level = PRIVATE)
public class MaterialType {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "material_types_seq")
  @SequenceGenerator(name = "material_types_seq", sequenceName = "material_types_seq", allocationSize = 1)
  Long id;

  @Column(name = "name")
  String name;

  @OneToMany(mappedBy = "materialType", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<Material> materials;

  @Column(name = "active")
  Boolean active;

}
