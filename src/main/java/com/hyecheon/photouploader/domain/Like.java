package com.hyecheon.photouploader.domain;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "likes")
public class Like {
    @GraphQLQuery
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GraphQLQuery
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @GraphQLQuery
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
