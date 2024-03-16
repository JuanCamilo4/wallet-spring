package com.siscode.wallet.app.infraestructure;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document("debt")
public class DebtEntity {

    @Id
    private String _id;
    private String type;
    private String person;
    private String description;
    @DBRef
    private AccountEntity account;
    private BigDecimal initAmount;
    private BigDecimal currentAmount;
    private String createdAt;
    private String updatedAt;

}
