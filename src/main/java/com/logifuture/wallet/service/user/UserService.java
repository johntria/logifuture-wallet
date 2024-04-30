package com.logifuture.wallet.service.user;

import com.logifuture.wallet.entitity.User;
import com.logifuture.wallet.exceptions.user.UserNotFoundException;
import com.logifuture.wallet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for managing user-related operations.
 */
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds a user by ID.
     *
     * @param userId The ID of the user to find.
     * @return The user entity.
     * @throws UserNotFoundException if no user with the specified ID is found.
     */
    public User findUserById(Long userId) throws UserNotFoundException {
        log.info("Attempting to find user with ID: {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with ID: {} not found", userId);
            return new UserNotFoundException("User with id:" + userId + " not found");
        });
    }

}