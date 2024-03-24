package com.siscode.wallet.app.repository;

import com.siscode.wallet.app.infraestructure.CreditCardEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditCardRepository extends MongoRepository<CreditCardEntity, ObjectId> {
}
