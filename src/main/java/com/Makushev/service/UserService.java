package com.Makushev.service;

import com.Makushev.exception.UserException;
import com.Makushev.models.Role;
import com.Makushev.models.User;

import java.util.UUID;

public interface UserService {

    User createUser(User user);

    User getUserById(UUID UUID) throws UserException;

    User updateUserById(UUID UUID, User user) throws UserException;

    void deleteUser(UUID UUID) throws UserException; // сделать роль null




}
