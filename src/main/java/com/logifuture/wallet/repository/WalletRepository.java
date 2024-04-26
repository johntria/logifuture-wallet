package com.logifuture.wallet.repository;

import com.logifuture.wallet.entitites.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * This interface serves as the repository for {@link Wallet} entities.
 * It extends JpaRepository to provide CRUD operations for {@link Wallet} entities.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}