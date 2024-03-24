package com.siscode.wallet.app.repository;

import com.siscode.wallet.app.infraestructure.AccountHistoryEntity;
import com.siscode.wallet.app.service.AccountRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountHistoryRepository extends MongoRepository<AccountHistoryEntity, ObjectId>, AccountRepositoryCustom {

}
