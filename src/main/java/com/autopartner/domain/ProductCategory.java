package com.autopartner.domain;

import com.autopartner.api.dto.request.ProductCategoryRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.*;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_categories")
@FieldDefaults(level = PRIVATE)
@Builder
public class ProductCategory {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_categories_seq")
  @SequenceGenerator(
      name = "product_categories_seq",
      sequenceName = "product_categories_seq",
      allocationSize = 1)
  Long id;

  @Column
  String name;

  @Column
  Long companyId;

  @Column
  Long parentId;

  @Exclude
  @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
  @OrderBy("id asc")
  @JsonIgnore
  List<Material> materials;

  @Column
  @CreationTimestamp
  LocalDateTime createdAt;

  @Column
  @Builder.Default
  Boolean active = true;

  public static ProductCategory create(ProductCategoryRequest request, Long companyId) {
    return ProductCategory.builder()
        .name(request.getName())
        .companyId(companyId)
        .parentId(request.getParentId())
        .build();
  }

  public void update(ProductCategoryRequest request) {
    name = request.getName();
    parentId = request.getParentId();
  }

  public void delete() {
    active = false;
  }
}
