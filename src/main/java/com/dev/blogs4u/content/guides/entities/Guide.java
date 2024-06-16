package com.dev.blogs4u.content.guides.entities;

import com.dev.blogs4u.authentication.entities.UserEntity;
import com.dev.blogs4u.content.filters.entities.PLang;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "guides_")
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private int likes;
    private int views;

    private int price;

    private boolean isVisible;

    @OneToMany(mappedBy = "guide")
    private List<Module> modules;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreationTimestamp
    private Date dateOfCreation;

    @UpdateTimestamp
    private Date dateOfUpdate;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "guides_plangs",
            joinColumns = @JoinColumn(name = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "plang_id")
    )
    private List<PLang> pLangs;
}
