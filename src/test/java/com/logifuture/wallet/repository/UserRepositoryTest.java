package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitites.User;
import com.logifuture.wallet.entitites.Wallet;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * IT test for {@link UserRepository}
 */
@SpringBootTest
 class UserRepositoryTest {
    User savedUser;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Triantafyllakis");
        Wallet wallet = new Wallet();
        user.setWallet(wallet);
        savedUser = userRepository.save(user);
    }

    @AfterEach
    void afterEach() {
        userRepository.delete(savedUser);
    }

    @Test
    void testFindById() {
        Optional<User> optionalUser = userRepository.findById(savedUser.getId());
        assertTrue(optionalUser.isPresent());
        assertEquals("John", optionalUser.get().getFirstName());
        assertEquals("Triantafyllakis", optionalUser.get().getLastName());
    }
}