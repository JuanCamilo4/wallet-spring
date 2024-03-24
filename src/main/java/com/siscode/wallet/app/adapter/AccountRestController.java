package com.siscode.wallet.app.adapter;

import com.siscode.wallet.app.application.AccountApplication;
import com.siscode.wallet.app.application.mapper.AccountMapper;
import com.siscode.wallet.app.domain.AccountDomain;
import com.siscode.wallet.app.domain.AccountHistoryDomain;
import com.siscode.wallet.app.infraestructure.AccountEntity;
import com.siscode.wallet.app.utils.BusinessException;
import com.siscode.wallet.app.utils.ConstantsErrors;
import com.siscode.wallet.app.utils.ConstantsParams;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountRestController {

    private static final Logger log = LoggerFactory.getLogger(AccountRestController.class);

    @Autowired
    private AccountApplication accountApplication;

    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @GetMapping("/list-accounts")
    public ResponseEntity<List<AccountDomain>> listAccounts() {
        return ResponseEntity.ok(accountApplication.getListAccounts());
    }

    @GetMapping("/total-amount")
    public ResponseEntity<BigDecimal> totalAmount() {
        return ResponseEntity.ok(accountApplication.getTotalAmount());
    }

    @GetMapping("/account-used/{filter}")
    public ResponseEntity<List<String>> accountUsed(@PathVariable String filter) {
        log.debug(String.format("FILTER: %s", filter));
        if (!filter.equals(ConstantsParams.MONTH_ACCOUNT_FILTER.getValue()) && !filter.equals(ConstantsParams.DATE_ACCOUNT_FILTER.getValue())) {
            throw new BusinessException(ConstantsErrors.ERROR_FILTER_ACCOUNT_USED.getValue());
        }
        return ResponseEntity.ok(accountApplication.getAccountUsed(filter));
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(@RequestBody AccountDomain account){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountApplication.createAccount(account));
    }

    @PutMapping("/update-field")
    public ResponseEntity<AccountDomain> updateOneField(@RequestBody AccountDomain account) {
        AccountEntity entity = accountApplication.updateOneField(account);
        return ResponseEntity.status(201).body(mapper.AccountEntityToAccountDomain(entity));
    }

    @DeleteMapping("/delete-account/{id}")
    public ResponseEntity deleteAccount(@PathVariable String id) {
        accountApplication.deleteAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/list-account-history")
    private ResponseEntity<List<AccountHistoryDomain>> listAccountHistory(@RequestParam int limit) {
        return ResponseEntity.status(HttpStatus.OK).body(accountApplication.getAccountHistory(limit));
    }

}
