package com.logifuture.wallet.entitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * This class represents a User entity
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "users", schema = "public")
public class User {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", schema = "public", sequenceName = "users_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * The first name of the user.
     */
    @Column(name = "firstName")
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "lastName")
    private String lastName;


}