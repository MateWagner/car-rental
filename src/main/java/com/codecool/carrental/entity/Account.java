package com.codecool.carrental.entity;

import com.codecool.carrental.security.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @SequenceGenerator(
            name = "account_id_sequence",
            sequenceName = "account_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "account_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "password",
            nullable = false
    )
    @JsonIgnore
    private String password;

    @Column(
            name = "role",
            nullable = false
    )
    private Roles role;

    public Account(String email, String password, Roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
