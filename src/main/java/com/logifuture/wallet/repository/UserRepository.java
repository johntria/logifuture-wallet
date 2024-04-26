package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface serves as the repository for {@link User} entities.
 * It extends JpaRepository to provide CRUD operations for {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}