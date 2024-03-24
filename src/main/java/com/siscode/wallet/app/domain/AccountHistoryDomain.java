package com.siscode.wallet.app.domain;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

public class AccountHistoryDomain {

    private String id;
    private BigDecimal amount;
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
