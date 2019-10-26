package com.hyecheon.photouploader.repository;

import com.hyecheon.photouploader.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p  join fetch p.user u where p.caption like %:term% or p.location like %:term%")
    public List<Post> findByTerm(String term);
}
