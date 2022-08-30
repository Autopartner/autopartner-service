package com.autopartner.domain;

import com.autopartner.api.dto.request.CarTypeRequest;
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
@Table(name = "car_types")
@FieldDefaults(level = PRIVATE)
@Builder
public class CarType {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_types_seq")
    @SequenceGenerator(name = "car_types_seq", sequenceName = "car_types_seq", allocationSize = 1)
    Long id;

    @Column
    Long companyId;

    @Column
    String name;

    @OneToMany(mappedBy = "carType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("id asc")
    @JsonIgnore
    List<CarModel> models;
    @Column
    @Builder.Default
    Boolean active = true;

    @Column
    @CreationTimestamp
    LocalDateTime createdAt;

    public static CarType create(CarTypeRequest request, Long companyId) {
        return CarType.builder()
                .companyId(companyId)
                .name(request.getName())
                .build();
    }

    public void update(CarTypeRequest request) {
        name = request.getName();
    }

    public void delete() {
        active = false;
    }

}
