package com.Makushev.service.impl;

import com.Makushev.exception.UserException;
import com.Makushev.models.Role;
import com.Makushev.models.User;
import com.Makushev.repository.RoleRepository;
import com.Makushev.repository.UserRepository;
import com.Makushev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(User user) {
        if (user.getRole() == null && user.getRoleName() != null) {
            Role role = roleRepository.findByRoleName(user.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + user.getRoleName()));
            user.setRole(role);
        }

        return userRepository.save(user);
    }

    @Override
    @Cacheable("users")
    public User getUserById(UUID UUID) throws UserException {
        logger.info(" From BD: {}", UUID);
        return userRepository.findById(UUID)
                .orElseThrow(() -> new UserException("User not found with id: " + UUID));
    }

    @Override
    @CachePut(value = "users", key = "#UUID")
    public User updateUserById(UUID UUID, User updatedUser) throws UserException {
        logger.info("Update User: {}", UUID);

        User existingUser = userRepository.findById(UUID)
                .orElseThrow(() -> new UserException("User not found with id: " + UUID));

        existingUser.setFIO(updatedUser.getFIO());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setAvatar(updatedUser.getAvatar());

        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }
        else if (updatedUser.getRoleName() != null) {
            Role role = roleRepository.findByRoleName(updatedUser.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found: "
                            + updatedUser.getRoleName()));
            existingUser.setRole(role);
        }

        return userRepository.save(existingUser);
    }

    @Override
    @CacheEvict("users")
    public void deleteUser(UUID UUID) throws UserException {
        logger.info("Deleting User: {}", UUID);
        if (!userRepository.existsById(UUID)) {
            throw new UserException("User not found with id: " + UUID);
        }

        userRepository.deleteById(UUID);
    }
}
