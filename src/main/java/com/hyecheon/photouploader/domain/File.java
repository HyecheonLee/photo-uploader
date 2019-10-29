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
public class File {

    @GraphQLQuery
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GraphQLQuery
    @Column(columnDefinition = "TEXT")
    private String url;

    @GraphQLQuery
    @ManyToOne
    private Post post;

    public static File createFile(String url, Post post) {
        final var file = new File();
        file.url = url;
        file.post = post;
        return file;
    }
}
