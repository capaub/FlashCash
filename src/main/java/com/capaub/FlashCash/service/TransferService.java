package com.capaub.FlashCash.service;


import com.capaub.FlashCash.entity.Transfer;
import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.repository.TransferRepository;
import com.capaub.FlashCash.repository.UserAccountRepository;
import com.capaub.FlashCash.repository.UserRepository;
import com.capaub.FlashCash.service.form.TransferForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferService {

    @Autowired
    private final UserAccountRepository userAccountRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final TransferRepository transferRepository;

    public TransferService(UserAccountRepository userAccountRepository, UserRepository userRepository, TransferRepository transferRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userRepository = userRepository;
        this.transferRepository = transferRepository;
    }

    public void transferCashToFriend(TransferForm transferForm) {
        double amountToTransfer = transferForm.getAmountBeforeFee();
        double amountAfterFee = amountToTransfer * 1.05;

        User userFrom = userRepository.findUserByEmail(transferForm.getMailUserFrom()).get();
        User userTo = userRepository.findUserByEmail(transferForm.getMailUserTo()).get();

        LocalDateTime dateTime = LocalDateTime.now();

        userFrom.getUserAccount().setAmount(userFrom.getUserAccount().getAmount() - amountAfterFee);
        userAccountRepository.save(userFrom.getUserAccount());
        userFrom.setUserAccount(userAccountRepository.findById(userFrom.getUserAccount().getId()).get());

        userTo.getUserAccount().setAmount(userTo.getUserAccount().getAmount() + amountToTransfer);
        userAccountRepository.save(userTo.getUserAccount());
        userTo.setUserAccount(userAccountRepository.findById(userTo.getUserAccount().getId()).get());

        Transfer transfer = new Transfer();
        transfer.setUserFrom(userFrom);
        transfer.setUserTo(userTo);
        transfer.setAmountBeforeFee(amountToTransfer);
        transfer.setAmountAfterFee(amountAfterFee);
        transfer.setTransferDate(dateTime);

        transferRepository.save(transfer);
    }
}
