package com.siscode.wallet.app.application;

import com.siscode.wallet.app.infraestructure.CreditCardEntity;
import com.siscode.wallet.app.repository.CreditCardRepository;
import com.siscode.wallet.app.utils.BusinessException;
import com.siscode.wallet.app.utils.ConstantsParams;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Controller
public class CreditCardApplication {

    private static final Logger log = LoggerFactory.getLogger(CreditCardApplication.class);

    @Autowired
    private CreditCardRepository creditCardRepository;

    public Integer discountAmount(String id, BigDecimal amount, String type) {
        try {
            Optional<CreditCardEntity> entity = creditCardRepository.findById(new ObjectId(id));
            if (!entity.isPresent()) return 0;

            if (type.equals(ConstantsParams.TYPE_RECORD_EXPENSE.getValue())) {
                if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(entity.get().getBalanceAvailable()) > 0) return -1;
                entity.get().setBalanceAvailable(entity.get().getBalanceAvailable().subtract(amount));
            } else {
                if (amount.compareTo(BigDecimal.ZERO) < 0) return -1;
                entity.get().setBalanceAvailable(entity.get().getBalanceAvailable().add(amount));
            }
            entity.get().setUpdatedAt(new Date().toString());
            creditCardRepository.save(entity.get());
            return 1;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
