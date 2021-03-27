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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "car_models")
@FieldDefaults(level = PRIVATE)
public class CarModel {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_models_seq")
  @SequenceGenerator(name = "car_models_seq", sequenceName = "car_models_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "car_brand_id")
  @ManyToOne
  CarBrand carBrand;

  @Column(name = "name")
  String name;

  @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<Car> cars;

  @Column(name = "active")
  Boolean active;

}
