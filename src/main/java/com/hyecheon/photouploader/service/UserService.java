package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.dto.CreateUser;
import com.hyecheon.photouploader.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final LoginUserService userDetailsService;

    @GraphQLQuery(name = "users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GraphQLQuery(name = "isFollowing")
    public boolean isFollowing(@GraphQLContext User followingUser) {
        final var loginUser = userDetailsService.getLoginUser();
        return loginUser.getFollowing().contains(followingUser);
    }

    @GraphQLQuery(name = "userById")
    public Optional<User> userById(Long id) {
        return userRepository.findById(id);
    }

    @GraphQLQuery(name = "userByEmail")
    public Optional<User> userByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @GraphQLMutation(name = "createAccount")
    public User createAccount(@Valid CreateUser createUser, BindingResult result) {
        final var user = createUser.toUser();
        emailService.sendSecretMail("rainbow880616@gmail.com", "");
        return userRepository.save(user);
    }

    @GraphQLQuery(name = "searchUser")
    public List<User> searchUser(String term) {
        return userRepository.findByTerm(term);
    }

    @Transactional
    @GraphQLMutation(name = "following")
    public boolean followUser(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var optionalUser = userRepository.findById(id);
        final var followingUser = optionalUser.orElseThrow(() -> new RuntimeException("id error"));
        final var savedUser = userRepository.findById(user.getId()).get();
        savedUser.addFollowingUser(followingUser);
        return true;
    }

    @Transactional
    @GraphQLMutation(name = "unFollowing")
    public boolean unFollowUser(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final var optionalUser = userRepository.findById(id);
        final var followingUser = optionalUser.orElseThrow(() -> new RuntimeException("id error"));
        final var savedUser = userRepository.findById(user.getId()).get();
        savedUser.removeFollowingUser(followingUser);
        return true;
    }

    @Transactional
    @GraphQLMutation(name = "editUser")
    public User editUser(String username, String email, String firstName, String lastName, String bio) {
        final var loginUser = userDetailsService.getLoginUserWithInfo();

        if (StringUtils.hasText(username)) {
            loginUser.setUsername(username);
        }
        if (StringUtils.hasText(email)) {
            loginUser.setEmail(email);
        }
        if (StringUtils.hasText(firstName)) {
            loginUser.setFirstName(firstName);
        }
        if (StringUtils.hasText(lastName)) {
            loginUser.setLastName(lastName);
        }
        if (StringUtils.hasText(bio)) {
            loginUser.setBio(bio);
        }
        return loginUser;
    }

    @GraphQLQuery(name = "seeUser")
    public User seeUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("id error"));
    }

    @GraphQLQuery(name = "me")
    public User me() {
        return userDetailsService.getLoginUserWithInfo();
    }


}
