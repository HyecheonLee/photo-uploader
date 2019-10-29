package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.Post;
import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.repository.PostRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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

    @Transactional
    @GraphQLMutation(name = "editPost")
    public Post editPost(Long id, String caption, String location) {
        final var post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id error"));
        final var loginUser = loginUserService.getLoginUserWithInfo();
        if (post.getUser().equals(loginUser)) {
            post.setCaption(caption);
            post.setLocation(location);
            return post;
        } else {
            throw new RuntimeException("you can't do edit");
        }
    }

    @Transactional
    @GraphQLMutation(name = "deletePost")
    public boolean deletePost(Long id) {
        final var post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("id error"));
        final var loginUser = loginUserService.getLoginUserWithInfo();
        if (post.getUser().equals(loginUser)) {
            postRepository.delete(post);
            return true;
        } else {
            throw new RuntimeException("you can't do delete");
        }
    }

    @GraphQLQuery(name = "seeFeed")
    public List<Post> seeFeed() {
        final var loginUser = loginUserService.getLoginUserWithInfo();
        final var following = loginUser.getFollowing();
        return following.stream().map(User::getPosts)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Post::getUpdatedAt))
                .collect(Collectors.toList());
    }
}
