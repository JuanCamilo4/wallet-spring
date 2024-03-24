package com.siscode.wallet.app.application.mapper;

import com.siscode.wallet.app.domain.RecordDomain;
import com.siscode.wallet.app.domain.RecordDomainComplete;
import com.siscode.wallet.app.infraestructure.RecordEntity;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RecordMapper {

    @Mapping(target = "id", expression = "java(recordEntity.getId().toString())")
    @Mapping(target = "account", expression = "java(recordEntity.getAccount().toString())")
    @Mapping(target = "category", expression = "java(recordEntity.getCategory().toString())")
    RecordDomain RecordEntityToRecordDomain(RecordEntity recordEntity);

    @Mapping(target = "id", expression = "java(recordEntity.getId().toString())")
    @Mapping(target = "category", expression = "java(recordEntity.getCategory().toString())")
    RecordDomainComplete RecordEntityToRecordDomainComplete(RecordEntity recordEntity);

    default RecordEntity RecordDomainToRecordEntityIdNull(RecordDomain recordDomain) {
        if ( recordDomain == null ) {
            return null;
        }

        RecordEntity recordEntity = new RecordEntity();

        recordEntity.setAmount( recordDomain.getAmount() );
        recordEntity.setDate( recordDomain.getDate() );
        recordEntity.setTime( recordDomain.getTime() );
        recordEntity.setType( recordDomain.getType() );
        recordEntity.setPaymentType( recordDomain.getPaymentType() );
        recordEntity.setPlace( recordDomain.getPlace() );
        recordEntity.setNote( recordDomain.getNote() );
        recordEntity.setCreatedAt( recordDomain.getCreatedAt() );
        recordEntity.setUpdatedAt( recordDomain.getUpdatedAt() );

        recordEntity.setAccount( recordDomain.getAccount() != null ? mapId(recordDomain.getAccount()) : mapId(new String()) );
        recordEntity.setCategory( recordDomain.getCategory() != null ? mapId(recordDomain.getCategory()) : mapId(new String()) );

        return recordEntity;
    }

    default ObjectId mapId(String id) {
        return new ObjectId(id);
    }

    default ObjectId map(String id) {
        return new ObjectId(id);
    }

}
