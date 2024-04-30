package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitity.User;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.Random;

/**
 * IT test for {@link UserRepository}
 */
@Testcontainers
@SpringBootTest
class UserRepositoryITTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    User savedUser;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Triantafyllakis");
        savedUser = userRepository.save(user);
    }

    @AfterEach
    void afterEach() {
        userRepository.delete(savedUser);
    }
    /**
     * IT test for {@link UserRepository#findById(Object)}
     * GIVEN: User id which already exists
     * EXPECTED: To fetch user
     */
    @Test
    void testFindById_given_existing_user_expected_success_fetch() {
        Optional<User> optionalUser = userRepository.findById(savedUser.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals("John", optionalUser.get().getFirstName());
        assertEquals("Triantafyllakis", optionalUser.get().getLastName());
    }

    /**
     * IT test for {@link UserRepository#findById(Object)}
     * GIVEN: User id which already exists
     * EXPECTED: To return nullable {@link Optional<User>}
     */
    @Test
    void testFindById_given_not_existing_user_expected_nullable_fetch() {
        Optional<User> optionalUser = userRepository.findById(new Random().nextLong());
        assertTrue(optionalUser.isEmpty());
    }
}