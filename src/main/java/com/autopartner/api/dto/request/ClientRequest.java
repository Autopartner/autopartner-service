package com.autopartner.api.dto.request;

import com.autopartner.domain.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@Builder
public class ClientRequest {

  @NotNull
  @Size(min = 3, max = 256)
  String firstName;

  @Size(max = 256)
  String lastName;

  @Size(max = 256)
  String companyName;

  @Size(max = 256)
  String address;

  @NotNull
  @Pattern(regexp = "\\+\\d{12}")
  String phone;

  @Email
  String email;

  @Min(0)
  @Max(100)
  int productDiscount;

  @Min(0)
  @Max(100)
  int taskDiscount;

  ClientType clientType;

  @Size(max = 256)
  String note;
}
