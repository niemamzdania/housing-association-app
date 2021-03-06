package com.przemkeapp.housingassociationapp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users_details")
@Getter
@Setter
@NoArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @Size(min = 3, max = 30)
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 30)
    @NotNull
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "^(?:[0-9]{3}+-+[0-9]{3}+-+[0-9]{3}|)$",
            message = "It must be expression in this type: 000-000-000")
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "photo")
    private byte[] photo;

    public UserDetail(String firstName, String lastName, String phoneNumber, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void changeAddress(Address newAddress) {
        if (this.address == null) {
            this.address = new Address();
            this.address.setId(0);
        }
        this.address.setCity(newAddress.getCity());
        this.address.setStreet(newAddress.getStreet());
        this.address.setPostalCode(newAddress.getPostalCode());
    }
}
