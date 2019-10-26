package com.hyecheon.photouploader.repository;

import com.hyecheon.photouploader.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
