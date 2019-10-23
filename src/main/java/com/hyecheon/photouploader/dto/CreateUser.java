package com.hyecheon.photouploader.dto;

import com.hyecheon.photouploader.domain.User;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateUser {
    @GraphQLQuery
    @NotEmpty
    private String username;
    @GraphQLQuery
    @NotEmpty
    private String email;
    @GraphQLQuery
    private String firstName = "";
    @GraphQLQuery
    private String lastName = "";
    @GraphQLQuery
    private String bio = "";

    public User toUser() {
        return User.createUser(this);
    }
}
