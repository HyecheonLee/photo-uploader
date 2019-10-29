package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.Like;
import com.hyecheon.photouploader.domain.Post;
import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.repository.LikeRepository;
import com.hyecheon.photouploader.repository.PostRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final LoginUserService loginUserService;

    @GraphQLQuery(name = "toggleLike")
    public boolean getToggleLike(Long postId) {
        User user = loginUserService.getLoginUser();
        final var post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("id error"));
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

    @GraphQLQuery(name = "isLiked")
    public boolean isLiked(@GraphQLContext Post post) {
        return likeRepository.existsByUserAndPost(loginUserService.getLoginUser(), post);
    }

    @GraphQLQuery(name = "likeCount")
    public int likeCount(@GraphQLContext Post post) {
        return likeRepository.countLikeByPost(post);
    }
}
