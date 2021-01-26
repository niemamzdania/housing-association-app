package com.przemkeapp.housingassociationapp.Entity;

import com.przemkeapp.housingassociationapp.validation.UniqueUserField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
@Getter @Setter
public class User {

    @Id
    @Column(name = "username")
    @Size(min = 3, max = 50 )
    @NotNull
    @UniqueUserField(value = "userName", message = "This username exist in database, type another one")
    private String userName;

    @Column(name = "password")
    @Size(min = 6, max = 68)
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
