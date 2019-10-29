package com.hyecheon.photouploader.domain;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @GraphQLQuery
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @GraphQLQuery
    private String caption;
    @GraphQLQuery
    private String location;

    @GraphQLQuery
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @GraphQLQuery
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @GraphQLQuery
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static Post createPost(String caption, User user) {
        final var post = new Post();
        post.caption = caption;
        post.user = user;
        return post;
    }

    public static Post createPost(String caption, User user, List<String> urls) {
        final var post = createPost(caption, user);
        post.files = urls.stream().map(url -> File.createFile(url, post)).collect(Collectors.toList());
        return post;
    }
}
