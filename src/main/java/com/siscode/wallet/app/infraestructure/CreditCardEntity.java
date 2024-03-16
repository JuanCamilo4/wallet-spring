package com.siscode.wallet.app.infraestructure;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document("creditCard")
public class CreditCardEntity {

    @Id
    private String _id;
    private String name;
    private String bank;
    private BigDecimal balanceTotal;
    private BigDecimal balanceAvailable;
    private String color;
    private String datePayment;
    private String dateCourt;

}
