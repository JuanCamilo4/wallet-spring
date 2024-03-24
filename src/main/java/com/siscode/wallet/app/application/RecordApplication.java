package com.siscode.wallet.app.application;

import com.siscode.wallet.app.application.mapper.RecordMapper;
import com.siscode.wallet.app.domain.RecordDomain;
import com.siscode.wallet.app.domain.RecordDomainComplete;
import com.siscode.wallet.app.domain.RecordDomainPagination;
import com.siscode.wallet.app.infraestructure.RecordEntity;
import com.siscode.wallet.app.repository.RecordRepository;
import com.siscode.wallet.app.utils.BusinessException;
import com.siscode.wallet.app.utils.ConstantsErrors;
import com.siscode.wallet.app.utils.ConstantsParams;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class RecordApplication {

    private static final Logger log = LoggerFactory.getLogger(RecordApplication.class);

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private CreditCardApplication creditCardApplication;

    @Autowired
    private AccountApplication accountApplication;

    RecordMapper mapper = Mappers.getMapper(RecordMapper.class);

    public RecordDomain addRecord(RecordDomain record) {
        log.debug(">>> STARTING ADD RECORD");
        int responseAccount = 2;
        if (record.getFourmil()) {
            BigDecimal amountAux = (record.getAmount().multiply(BigDecimal.valueOf(4))).divide(new BigDecimal(1000));
            record.setAmount(record.getAmount().add(amountAux));
        }
        if (record.getPaymentType().equals(ConstantsParams.PAYMENT_CASH.getValue()) || record.getPaymentType().equals(ConstantsParams.PAYMENT_DEBT_CARD.getValue())) {
            log.debug(">>> DISCOUNT CASH");
            responseAccount = accountApplication.discountAmount(record.getAccount(), record.getAmount(), record.getType());
        }
        if (record.getPaymentType().equals(ConstantsParams.PAYMENT_CREDIT_CARD.getValue())) {
            log.debug(">>> DISCOUNT CREDIT");
            responseAccount = creditCardApplication.discountAmount(record.getAccount(), record.getAmount(), record.getType());
        }

        if (responseAccount == 0) {
            log.debug(String.format(">>> ERROR CREDIT CARD %s", ConstantsErrors.ERROR_MISSING_CREDIT_ACCOUNT.getValue()));
            throw new BusinessException(ConstantsErrors.ERROR_MISSING_CREDIT_ACCOUNT.getValue());
        } else if (responseAccount == -1) {
            log.debug(String.format(">>> ERROR CREDIT CARD %s", ConstantsErrors.ERROR_DISCOUNT_CREDIT_CARD.getValue()));
            throw new BusinessException(ConstantsErrors.ERROR_DISCOUNT_CREDIT_CARD.getValue());
        }

        record.setCreatedAt(new Date().toString());
        record.setUpdatedAt(new Date().toString());
        try {
            RecordEntity recordEntity = mapper.RecordDomainToRecordEntityIdNull(record);
            recordRepository.save(recordEntity);
            return record;
        } catch (Exception e) {
            log.debug(String.format(">>> ERROR %s", e.getMessage()));
            throw new BusinessException(ConstantsErrors.ERROR_SAVE_RECORD.getValue());
        }
    }

    public RecordDomainPagination listRecord(Integer limit, Integer skip, String ...filter) {
        Map map = mapFiltersFindAll(filter);

        Page<RecordEntity> page = recordRepository.findAllWithFilters(map, skip, limit);
        List<RecordDomainComplete> listRecord = new ArrayList<>();

        for (RecordEntity record : page.getContent()) {
            RecordDomainComplete auxRecord = mapper.RecordEntityToRecordDomainComplete(record);
            auxRecord.setId(record.getId().toHexString());
            //TODO Hacer lo mismo que se hace en account pero con  category
            auxRecord.setCategory(record.getCategory().toHexString());
            auxRecord.setAccount(accountApplication.getAccount(record.getAccount()));
            listRecord.add(auxRecord);
        }

        RecordDomainPagination recordPage = new RecordDomainPagination(
                listRecord,
                page.getTotalElements(),
                page.getPageable().getPageNumber(),
                page.getNumberOfElements(),
                page.getTotalPages());

        return recordPage;
    }

    private Map<String, String> mapFiltersFindAll(String ...filters) {
        List<String> list = Arrays.stream(filters).toList();
        Map<String, String> map = new HashMap<>();
        for (String item : list) {
            String[] parts = item.split("==");
            map.put(parts[0], parts[1]);
        }
        return map;
    }

}
