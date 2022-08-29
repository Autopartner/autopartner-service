package com.autopartner.api.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CarModelRequest {

    @NotNull
    @Size(max = 255)
    String name;

    @NotNull
    Long carBrandId;

    @NotNull
    Long carTypeId;
}
