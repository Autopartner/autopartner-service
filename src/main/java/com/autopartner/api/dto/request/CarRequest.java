package com.autopartner.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.Year;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CarRequest {

  @NotNull
  @Size(min = 3, max = 256)
  String vinCode;

  @NotNull
  @Size(min = 4, max = 8)
  String plateNumber;

  @NotNull
  Year manufactureYear;

  @Size(max = 256)
  String note;

  @NotNull
  Long carModelId;

  @NotNull
  Long clientId;

}
