package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface serves as the repository for {@link Wallet} entities.
 * It extends JpaRepository to provide CRUD operations for {@link Wallet} entities.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userID);
}