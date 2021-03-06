package com.przemkeapp.housingassociationapp.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "announcements")
@Getter @Setter
public class Announcement {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    @Size(min = 3, max = 100)
    private String title;

    @Column(name = "content")
    @Size(max = 10000)
    private String content;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    @JsonManagedReference
    private User author;

    @OneToMany(mappedBy = "announcement",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    public Announcement() {
        this.comments = new ArrayList<>();
    }
}
