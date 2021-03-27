package com.autopartner.domain;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
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
@Table(name = "orders")
@FieldDefaults(level = PRIVATE)
public class Order {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
  @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", allocationSize = 1)
  Long id;

  @Column(name = "order_number")
  String orderNumber;

  @JoinColumn(name = "car_id")
  @ManyToOne
  Car car;

  @Column(name = "mileage")
  Double mileage;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  Status status;

  @Column(name = "note")
  String note;

  @Column(name = "created_at")
  LocalDateTime createdAt;

  @Column(name = "updated_at")
  LocalDateTime updatedAt;

  @Column(name = "active")
  Boolean active;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<OrderTask> orderTasks;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<OrderMaterial> orderMaterials;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onPreUpdate() {
    updatedAt = LocalDateTime.now();
  }

  private enum Status {
    NEW,
    IN_PROGRESS,
    CLOSED,
    CANCELLED
  }

}
