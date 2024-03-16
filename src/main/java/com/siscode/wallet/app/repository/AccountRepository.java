package com.siscode.wallet.app.repository;

import com.siscode.wallet.app.infraestructure.AccountEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<AccountEntity, ObjectId> {

}
