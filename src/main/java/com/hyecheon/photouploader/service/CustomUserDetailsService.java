package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public User loadUserById(Long id) {
        final Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
