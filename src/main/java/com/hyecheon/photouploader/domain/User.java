package com.hyecheon.photouploader.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyecheon.photouploader.dto.CreateUser;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class User implements UserDetails {

    @GraphQLQuery
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true)
    @GraphQLQuery
    private String username;

    @Setter
    @Column(unique = true)
    @GraphQLQuery
    private String email;

    private String password;

    @Setter
    @GraphQLQuery
    private String firstName;

    @Setter
    @GraphQLQuery
    private String lastName;

    @Setter
    @GraphQLQuery
    private String bio;

    @GraphQLQuery
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> followers = new HashSet<>();

    @GraphQLQuery
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<User> following = new HashSet<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public void addFollowingUser(User user) {
        following.add(user);
    }

    public void removeFollowingUser(User user) {
        following.removeIf(user1 -> user1.getId().equals(user.getId()));
    }

    public static User createUser(CreateUser createUser) {
        final var user = new User();
        user.username = createUser.getUsername();
        user.email = createUser.getEmail();
        user.firstName = createUser.getFirstName();
        user.lastName = createUser.getLastName();
        user.bio = createUser.getBio();
        return user;
    }

    /*
     * UserDetails interface methods
     * */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
