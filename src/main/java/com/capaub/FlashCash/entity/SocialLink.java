package com.capaub.FlashCash.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SocialLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user1;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user2;
}