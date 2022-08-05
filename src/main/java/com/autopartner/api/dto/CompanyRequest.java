package com.autopartner.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class CompanyRequest {

  @NotEmpty
  @Size(min = 3, max = 256)
  String name;

  @NotEmpty
  @Size(min = 3, max = 256)
  String country;

  @NotEmpty
  @Size(min = 3, max = 256)
  String city;

}
