package com.autopartner.api.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CarTypeRequest {

    @NotNull
    @Size(min = 3, max = 256)
    String name;
}
