package com.przemkeapp.housingassociationapp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "username", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ElementCollection
    @CollectionTable(name = "authorities",
                    joinColumns = @JoinColumn(name = "username"))
    @Column(name = "authority")
    private Set<String> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details_id")
    private UserDetail userDetail;

    @Column(name = "email", nullable = false)
    private String email;

    public User(String userName, String password, boolean enabled, Set<String> roles,
                UserDetail userDetail, String email) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.userDetail = userDetail;
        this.email = email;
    }
}
