package com.siscode.wallet.app.infraestructure;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("record")
public class RecordEntity {

    @Id
    private String _id;
    @DBRef
    private AccountEntity account;
    private BigDecimal amount;
    private String date;
    private String time;
    @DBRef
    private CategoryEntity category;
    private String type;
    private String paymentType;
    private String place;
    //private String creditCard;
    private String note;
    //private String debt;
    private String createdAt;
    private String updatedAt;

}
