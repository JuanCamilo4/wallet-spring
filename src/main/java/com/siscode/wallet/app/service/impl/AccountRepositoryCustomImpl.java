package com.siscode.wallet.app.service.impl;

import com.siscode.wallet.app.infraestructure.AccountHistoryEntity;
import com.siscode.wallet.app.service.AccountRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@Component
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(AccountRepositoryCustomImpl.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AccountRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<AccountHistoryEntity> findLastAccount() {
        log.debug(String.format(">>> START FIND LAST ACCOUNT"));
        Query query = new Query().limit(1).with(Sort.by(Sort.Direction.DESC, "createdAt"));
        log.debug(String.format(">>> CONSULT DB %s", mongoTemplate.findOne(query, AccountHistoryEntity.class)));
        return Optional.of(mongoTemplate.findOne(query, AccountHistoryEntity.class));
    }

    @Override
    public List<AccountHistoryEntity> findTopDocuments(int limit) {
        Query query = new Query().limit(limit);
        return mongoTemplate.find(query, AccountHistoryEntity.class);
    }

}
