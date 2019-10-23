package com.hyecheon.photouploader.domain;

import com.hyecheon.photouploader.dto.CreateUser;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @GraphQLQuery
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @GraphQLQuery
    private String username;

    @Column(unique = true)
    @GraphQLQuery
    private String email;

    @GraphQLQuery
    private String firstName;

    @GraphQLQuery
    private String lastName;

    @GraphQLQuery
    private String bio;

    @GraphQLQuery
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> followers = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> following = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public static User createUser(CreateUser createUser) {
        final var user = new User();
        user.username = createUser.getUsername();
        user.email = createUser.getEmail();
        user.firstName = createUser.getFirstName();
        user.lastName = createUser.getLastName();
        user.bio = createUser.getBio();
        return user;
    }

}
