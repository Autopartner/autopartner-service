package com.autopartner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

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
