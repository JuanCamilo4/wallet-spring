package com.siscode.wallet.app.repository;

import com.siscode.wallet.app.infraestructure.RecordEntity;
import com.siscode.wallet.app.service.RecordRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecordRepository extends MongoRepository<RecordEntity, ObjectId>, RecordRepositoryCustom {
}
