package com.przemkeapp.housingassociationapp.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    @Column(name = "username")
    @Size(min = 3, max = 50 )
    @NotNull
    private String userName;

    @Column(name = "password")
    @Size(min = 8, max = 68)
    @NotNull
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ElementCollection
    @CollectionTable(name = "authorities",
            joinColumns = @JoinColumn(name = "username"))
    @Column(name = "authority")
    private Set<String> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    @NotNull
    private UserDetail userDetail;

    @Column(name = "email")
    @NotNull
    @NotEmpty
    private String email;

    public User() {
        this.userDetail = new UserDetail();
    }

    public User(String userName, String password, Set<String> roles,
                UserDetail userDetail, String email) {
        this.userName = userName;
        this.password = password;
        this.enabled = true;
        this.roles = roles;
        this.userDetail = userDetail;
        this.email = email;
    }
}
