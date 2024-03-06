package com.capaub.FlashCash.service.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TransferForm {
    private String mailUserFrom;
    private String mailUserTo;
    @NotBlank(message = "amount is required")
    private Double amountBeforeFee;
}
