package com.capaub.FlashCash.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    @Column(unique = true)
    private String iban1;
    @Column(unique = true)
    private String iban2;
    @Column(unique = true)
    private String iban3;
    @Column(unique = true)
    private String iban4;
    @Column(unique = true)
    private String iban5;
}