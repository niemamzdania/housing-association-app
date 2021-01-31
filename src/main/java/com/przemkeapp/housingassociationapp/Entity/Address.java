package com.przemkeapp.housingassociationapp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "addresses")
@Getter @Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "city")
    @Size(max = 30)
    private String city;

    @Column(name = "street")
    @Size(max = 30)
    private String street;

    @Column(name = "postal_code")
    @Pattern(regexp = "[0-9-]{0,6}",
            message = "only numbers and dashes are allowed")
    @Size(max = 6)
    private String postalCode;

    public Address(String city, String street, String postalCode) {
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
