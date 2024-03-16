package com.siscode.wallet.app.application.mapper;

import com.siscode.wallet.app.domain.AccountDomain;
import com.siscode.wallet.app.infraestructure.AccountEntity;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    @Mapping(target = "id", expression = "java(accountEntity.getId().toString())")
    AccountDomain AccountEntityToAccountDomain(AccountEntity accountEntity);

    AccountEntity AccountDomainToAccountEntity(AccountDomain accountDomain);

    default ObjectId map(String id) {
        return new ObjectId(id);
    }

}
