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
public class Comment {

    @GraphQLQuery
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GraphQLQuery
    private String text;

    @GraphQLQuery
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @GraphQLQuery
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public static Comment createComment(String text, User user, Post post) {
        final var comment = new Comment();
        comment.text = text;
        comment.user = user;
        comment.post = post;
        return comment;
    }
}
