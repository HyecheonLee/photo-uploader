package com.hyecheon.photouploader.repository;

import com.hyecheon.photouploader.domain.Like;
import com.hyecheon.photouploader.domain.Post;
import com.hyecheon.photouploader.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("select t from Like t where t.user.id=:userId and t.post.id=:postId")
    public Optional<Like> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    public Optional<Like> findByUserAndPost(User user, Post post);
}
