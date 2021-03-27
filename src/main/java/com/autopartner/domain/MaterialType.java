package com.autopartner.domain;


import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
