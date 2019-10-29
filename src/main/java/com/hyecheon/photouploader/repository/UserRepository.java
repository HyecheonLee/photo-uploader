package com.hyecheon.photouploader.repository;

import com.hyecheon.photouploader.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.username like %:term% or u.firstName like %:term% or u.lastName like %:term%")
    List<User> findByTerm(String term);

}
