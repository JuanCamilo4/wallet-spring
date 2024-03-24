package com.siscode.wallet.app.application;

import com.siscode.wallet.app.application.mapper.AccountMapper;
import com.siscode.wallet.app.domain.AccountDomain;
import com.siscode.wallet.app.domain.AccountHistoryDomain;
import com.siscode.wallet.app.infraestructure.AccountEntity;
import com.siscode.wallet.app.infraestructure.AccountHistoryEntity;
import com.siscode.wallet.app.repository.AccountHistoryRepository;
import com.siscode.wallet.app.repository.AccountRepository;
import com.siscode.wallet.app.utils.BusinessException;
import com.siscode.wallet.app.utils.ConstantsErrors;
import com.siscode.wallet.app.utils.ConstantsParams;
import org.bson.types.ObjectId;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class AccountApplication {

    private static final Logger log = LoggerFactory.getLogger(AccountApplication.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHistoryRepository accountHistoryRepository;

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

                Optional<AccountHistoryEntity> optionalAccount = accountHistoryRepository.findLastAccount();
                log.debug(String.format(">>> OPTIONAL ACCOUNT %s", optionalAccount.isPresent()));
                AccountHistoryEntity accountHistory = new AccountHistoryEntity();
                accountHistory.setCreatedAt(new Date());
                if (optionalAccount.isPresent()) {
                    accountHistory.setAmount(optionalAccount.get().getAmount().add(accountEntity.getAmount()));
                } else {
                    accountHistory.setAmount(accountEntity.getAmount());
                }

                accountHistoryRepository.save(accountHistory);

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
                throw new BusinessException(ConstantsErrors.ERROR_UPDATE_NOT_FOUND_ACCOUNT.getValue());
            }
        } catch (Exception e) {
            log.debug(String.format(">>> ERROR %s", e.getMessage()));
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
            throw new BusinessException(ConstantsErrors.ERROR_UPDATE_NOT_FOUND_ACCOUNT.getValue());
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
                throw new BusinessException(ConstantsErrors.ERROR_UPDATE_NOT_FOUND_ACCOUNT.getValue());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Integer discountAmount(String id, BigDecimal amount, String type) {
        try {
            Optional<AccountEntity> entity = accountRepository.findById(new ObjectId(id));
            if (!entity.isPresent()) return 0;

            if (type.equals(ConstantsParams.TYPE_RECORD_EXPENSE.getValue())) {
                if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(entity.get().getAmount()) > 0) return -1;
                entity.get().setAmount(entity.get().getAmount().subtract(amount));
            } else {
                if (amount.compareTo(BigDecimal.ZERO) < 0) return -1;
                entity.get().setAmount(entity.get().getAmount().add(amount));
            }
            entity.get().setMonthUsed(entity.get().getMonthUsed()+1);
            entity.get().setNumberUsed((int) new Date().getTime());
            entity.get().setUpdatedAt(new Date().toString());
            accountRepository.save(entity.get());
            return 1;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public AccountDomain getAccount(ObjectId id) {
        Optional<AccountEntity> account = accountRepository.findByIdName(id);
        if (account.isPresent()) {
            return mapper.AccountEntityToAccountDomain(account.get());
        }
        return null;
    }

    public List<AccountHistoryDomain> getAccountHistory(int limit) {
        List<AccountHistoryEntity> listEntity = accountHistoryRepository.findTopDocuments(limit);
        List<AccountHistoryDomain> out = new ArrayList<>();
        for (AccountHistoryEntity account : listEntity) {
            AccountHistoryDomain aux = new AccountHistoryDomain();
            aux.setId(account.getId().toString());
            aux.setAmount(account.getAmount());
            aux.setCreatedAt(account.getCreatedAt());
            out.add(aux);
        }
        return out;
    }

}
