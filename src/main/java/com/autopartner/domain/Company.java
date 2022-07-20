package com.autopartner.domain;

import com.autopartner.DTO.CompanyRegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "companies")
@Data
@FieldDefaults(level = PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", sequenceName = "company_seq", allocationSize = 1)
    Long id;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "country")
    String country;

    @Column(name = "city")
    String city;

    @Column(name = "active")
    Boolean active;

    public static Company createCompany(CompanyRegistrationRequest request) {
        return Company.builder()
                .companyName(request.getCompanyName())
                .country(request.getCountry())
                .city(request.getCity())
                .build();
    }
}
