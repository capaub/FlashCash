package com.capaub.FlashCash.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User userFrom;
    @ManyToOne(fetch = FetchType.EAGER)
    private User userTo;
    private double amountBeforeFee;
    private double amountAfterFee;
    private LocalDateTime transferDate;
}