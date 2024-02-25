package com.capaub.FlashCash.service.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCashCbForm {
    @NotBlank(message = "cbNumber is required")
    private String cbNumber;
    @NotBlank(message = "date is required")
    private String date;
    @NotBlank(message = "cryptogram is required")
    private String cryptogram;
    @NotBlank(message = "amount is required")
    private double amount;
}