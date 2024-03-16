package com.siscode.wallet.app.application;

import com.siscode.wallet.app.application.mapper.AccountMapper;
import com.siscode.wallet.app.domain.AccountDomain;
import com.siscode.wallet.app.infraestructure.AccountEntity;
import com.siscode.wallet.app.repository.AccountRepository;
import com.siscode.wallet.app.utils.BusinessException;
import com.siscode.wallet.app.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

@Controller
public class AccountApplication {

    private static final Logger log = LoggerFactory.getLogger(AccountApplication.class);

    @Autowired
    private AccountRepository accountRepository;

    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    public List<AccountDomain> getListAccounts() {
        List<AccountDomain> list = new ArrayList<>();
        accountRepository.findAll()
                .stream()
                .forEach(e -> {
                    list.add(mapper.AccountEntityToAccountDomain(e));
                });
        return list;
    }

    public BigDecimal getTotalAmount() {
        List<BigDecimal> list = new ArrayList<>();
        accountRepository.findAll()
                .stream()
                .forEach(e -> {
                    list.add(e.getAmount());
                });
        BigDecimal total = list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    public List<String> getAccountUsed(String filter) {
        List<String> list = new ArrayList<>();
        Comparator<AccountEntity> comparator;
        List<AccountEntity> listAccounts =  accountRepository.findAll();

        if (filter.equals("all")) comparator = comparatorNumberUsed();
        else comparator = comparatorMonthUsed();

        List<AccountEntity> aux = listAccounts.stream().sorted(comparator).toList();
        aux.stream().forEach(e -> log.debug(String.format("AUX: %s", e.getName())));
        aux.stream().forEach(e -> {
            if (list.size() <= 5) list.add(e.getName());
        });
        return list;
    }

    private Comparator<AccountEntity> comparatorNumberUsed() {
        return Comparator.comparing(AccountEntity::getNumberUsed).reversed();
    }

    private Comparator<AccountEntity> comparatorMonthUsed() {
        return Comparator.comparing(AccountEntity::getMonthUsed).reversed();
    }

    public String createAccount(AccountDomain account) {
        log.debug(String.format("id: %s", account.getId()));
        try {
            if (account.getId() == null) {
                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setName(account.getName());
                accountEntity.setAmount(account.getAmount());
                accountEntity.setColor(account.getColor());
                accountEntity.setNumberUsed(Integer.valueOf(0));
                accountEntity.setMonthUsed(Integer.valueOf(0));
                accountEntity.setStatus(1);
                accountEntity.setCreatedAt(new Date().toString());
                accountRepository.save(accountEntity);
                return "CREATED";
            } else {
                Optional<AccountEntity> entity = accountRepository.findById(new ObjectId(account.getId()));
                if (entity.isPresent()) {
                    entity.get().setName(account.getName());
                    entity.get().setColor(account.getColor());
                    entity.get().setUpdatedAt(new Date().toString());
                    accountRepository.save(entity.get());
                    return "UPDATED";
                }
                throw new BusinessException(Constants.ERROR_UPDATE_NOT_FOUND_ACCOUNT.getValue());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public AccountEntity updateOneField(AccountDomain account) {
        log.debug(String.format("START UPDATE ONE FIELD"));
        try {
            Optional<AccountEntity> entity = accountRepository.findById(new ObjectId(account.getId()));
            if (entity.isPresent()) {
                if (account.getName() != null) entity.get().setName(account.getName());
                if (account.getColor() != null) entity.get().setColor(account.getColor());
                if (account.getStatus() != null) entity.get().setStatus(account.getStatus());
                entity.get().setUpdatedAt(new Date().toString());
                accountRepository.save(entity.get());
                return entity.get();
            }
            throw new BusinessException(Constants.ERROR_UPDATE_NOT_FOUND_ACCOUNT.getValue());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void deleteAccount(String id) {
        try {
            Boolean entity = accountRepository.existsById(new ObjectId(id));
            if (entity) {
                accountRepository.deleteById(new ObjectId(id));
            } else {
                throw new BusinessException(Constants.ERROR_UPDATE_NOT_FOUND_ACCOUNT.getValue());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

}
