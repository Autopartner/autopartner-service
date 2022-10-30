package com.autopartner.domain;

import com.autopartner.api.dto.request.TaskCategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "task_categories")
@FieldDefaults(level = PRIVATE)
@Builder
public class TaskCategory {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_categories_seq")
  @SequenceGenerator(
      name = "task_categories_seq",
      sequenceName = "task_categories_seq",
      allocationSize = 1)
  Long id;

  @Column
  Long companyId;

  @Column
  Long parentId;

  @Column
  String name;

  @ToString.Exclude
  @OneToMany(mappedBy = "taskCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy("id asc")
  @JsonIgnore
  List<Task> tasks;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @Builder.Default
  Boolean active = true;

  public static TaskCategory create(TaskCategoryRequest request, Long companyId) {
    return TaskCategory.builder()
        .name(request.getName())
        .parentId(request.getParentId())
        .companyId(companyId)
        .build();
  }

  public void update(TaskCategoryRequest request) {
    name = request.getName();
    parentId = request.getParentId();
  }

  public void delete() {
    active = false;
  }
}
