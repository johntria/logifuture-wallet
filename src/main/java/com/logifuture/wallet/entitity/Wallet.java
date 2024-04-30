package com.logifuture.wallet.entitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * This class represents a Wallet entity
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "wallet",schema = "public")
public class Wallet {

    /**
     * The unique identifier for the wallet.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq",schema = "public",sequenceName = "wallet_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * The balance of the wallet.
     */
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * The user associated with the wallet.
     */
    @OneToOne
    private User user;

    /**
     * The state of the wallet.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StateOfWallet state;
}