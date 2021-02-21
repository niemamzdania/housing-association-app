package com.przemkeapp.housingassociationapp.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.przemkeapp.housingassociationapp.validation.UniqueUserField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "username")
    @Size(min = 3, max = 50)
    @NotNull
    @UniqueUserField(value = "userName",
            message = "Account with this username already exist, type another one")
    private String userName;

    @Column(name = "password")
    @Size(min = 6, max = 68)
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
    @UniqueUserField(value = "email",
            message = "Account with this e-mail address already exist")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    @JsonBackReference
    private Community community;

    @OneToMany(mappedBy = "author",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonBackReference
    private List<Announcement> announcementList;

    @OneToMany(mappedBy = "author",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonBackReference
    private List<Comment> commentList;

    public User() {
        this.userDetail = new UserDetail();
        this.announcementList = new ArrayList<>();
        this.commentList = new ArrayList<>();
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void changeUserDetail(UserDetail newUserDetail) {
        if (this.userDetail == null) {
            this.userDetail = new UserDetail();
            this.userDetail.setId(0);
        }
        this.userDetail.setFirstName(newUserDetail.getFirstName());
        this.userDetail.setLastName(newUserDetail.getLastName());
        this.userDetail.setPhoneNumber(newUserDetail.getPhoneNumber());
        this.userDetail.setPhoto(newUserDetail.getPhoto());
        this.userDetail.changeAddress(newUserDetail.getAddress());
    }

    public void changeData(User data) {
        this.setUserName(data.getUserName());
        this.setEmail(data.getEmail());
        this.setPassword(data.getPassword());
        this.setRoles(data.getRoles());
        this.setEnabled(data.getEnabled());
        this.setCommunity(data.getCommunity());
        this.setAnnouncementList(data.getAnnouncementList());
        this.setCommentList(data.getCommentList());
    }
}
