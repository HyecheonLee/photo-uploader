package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.File;
import com.hyecheon.photouploader.domain.Like;
import com.hyecheon.photouploader.domain.Post;
import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.repository.PostRepository;
import com.hyecheon.photouploader.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@GraphQLApi
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final LoginUserService loginUserService;

    @GraphQLQuery(name = "searchPost")
    public List<Post> searchPost(String term) {
        return postRepository.findByTerm(term);
    }

    @GraphQLQuery(name = "seePost")
    public Post seePost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new RuntimeException("id error"));
    }

    @Transactional
    @GraphQLMutation(name = "upload")
    public Post upload(String caption, List<String> files) {
        final var loginUser = loginUserService.getLoginUser();
        final var post = Post.createPost(caption, loginUser, files);
        postRepository.save(post);
        return post;
    }
}
