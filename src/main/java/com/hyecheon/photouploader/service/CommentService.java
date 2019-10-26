package com.hyecheon.photouploader.service;


import com.hyecheon.photouploader.domain.Comment;
import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.repository.CommentRepository;
import com.hyecheon.photouploader.repository.PostRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@GraphQLApi
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GraphQLMutation(name = "addComment")
    public Comment addComment(String text, Long postId) {
        final var postOptional = postRepository.findById(postId);
        final var post = postOptional.orElseThrow(() -> new RuntimeException("found not postId"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var comment = Comment.createComment(text, user, post);
        return commentRepository.save(comment);
    }
}
