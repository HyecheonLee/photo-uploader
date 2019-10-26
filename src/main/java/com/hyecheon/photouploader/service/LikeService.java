package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.Like;
import com.hyecheon.photouploader.domain.Post;
import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.repository.LikeRepository;
import com.hyecheon.photouploader.repository.PostRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @GraphQLQuery(name = "toggleLike")
    public boolean getToggleLike(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            final var post = optionalPost.get();
            final var optionalLike = likeRepository.findByUserAndPost(user, post);
            if (optionalLike.isPresent()) {
                likeRepository.delete(optionalLike.get());
                return false;
            } else {
                final var like = new Like(user, post);
                likeRepository.save(like);
                return true;
            }
        }
        return false;
    }
}
