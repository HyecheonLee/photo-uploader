package com.hyecheon.photouploader.repository;

import com.hyecheon.photouploader.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
