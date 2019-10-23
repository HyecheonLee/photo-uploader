package com.hyecheon.photouploader.service;

import com.hyecheon.photouploader.domain.User;
import com.hyecheon.photouploader.dto.CreateUser;
import com.hyecheon.photouploader.repository.UserRepository;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@GraphQLApi
public class UserService {

    private final UserRepository userRepository;
    private EmailService emailService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GraphQLQuery(name = "allUsers")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GraphQLQuery(name = "userById")
    public Optional<User> userById(Long id) {
        return userRepository.findById(id);
    }

    @GraphQLMutation(name = "createAccount")
    public User createAccount(@Valid CreateUser createUser, BindingResult result) {
        final var user = createUser.toUser();
        emailService.sendSecretMail(user.getEmail(), "");
        return userRepository.save(user);
    }
}
