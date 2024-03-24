package com.siscode.wallet.app.service;

import com.siscode.wallet.app.infraestructure.AccountHistoryEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepositoryCustom {

    Optional<AccountHistoryEntity> findLastAccount();

    List<AccountHistoryEntity> findTopDocuments(int limit);

}
