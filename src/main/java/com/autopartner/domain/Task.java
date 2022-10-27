package com.autopartner.domain;


import com.autopartner.api.dto.request.TaskRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@FieldDefaults(level = PRIVATE)
@Builder
public class Task {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
  @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_seq", allocationSize = 1)
  Long id;

  @JoinColumn(name = "category_id")
  @ManyToOne
  TaskCategory category;

  @Column
  Long companyId;

  @Column
  String name;

  @Column
  BigDecimal price;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @UpdateTimestamp
  LocalDateTime updatedAt;

  @Column
  @Builder.Default
  Boolean active = true;

  public static Task create(TaskRequest request, TaskCategory taskCategory, Long companyId) {
    return Task.builder()
        .name(request.getName())
        .price(request.getPrice())
        .category(taskCategory)
        .companyId(companyId)
        .build();
  }

  public void update(TaskRequest request, TaskCategory taskCategory) {
    name = request.getName();
    price = request.getPrice();
    this.category = taskCategory;
  }

  public void delete() {
    active = false;
  }

}
