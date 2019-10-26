package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.Post;
import com.hyecheon.photouploader.repository.PostRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@GraphQLApi
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @GraphQLQuery(name = "searchPost")
    public List<Post> searchPost(String term) {
        return postRepository.findByTerm(term);
    }

    @GraphQLQuery(name = "seePost")
    public Post seePost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("id error"));
    }
}
