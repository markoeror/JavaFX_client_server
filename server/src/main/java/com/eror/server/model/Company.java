package com.eror.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyCode;

    private String companyName;

    private String address;

    private Long zipCode;

    private String city;

    private String currentAccount;

    private String tax_ID;

    private String phoneNumber;

    private String fax;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;


}
