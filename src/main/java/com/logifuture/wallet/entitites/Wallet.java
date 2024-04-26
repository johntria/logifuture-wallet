package com.logifuture.wallet.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "wallet")
public class Wallet {

    /**
     * The unique identifier for the wallet.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * The balance of the wallet.
     */
    @Column(name = "balance")
    private BigDecimal balance;

    /**
     * The user associated with the wallet.
     */
    @OneToOne(mappedBy = "wallet")
    private User user;

    /**
     * The state of the wallet.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private StateOfWallet state;
}