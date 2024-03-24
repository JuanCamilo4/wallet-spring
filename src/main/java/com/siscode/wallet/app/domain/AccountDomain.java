package com.siscode.wallet.app.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDomain {

    private String id;
    private String name;
    private BigDecimal amount;
    private Integer numberUsed;
    private Integer monthUsed;
    private String createdAt;
    private String updatedAt;
    private String color;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getNumberUsed() {
        return numberUsed;
    }

    public void setNumberUsed(Integer numberUsed) {
        this.numberUsed = numberUsed;
    }

    public Integer getMonthUsed() {
        return monthUsed;
    }

    public void setMonthUsed(Integer monthUsed) {
        this.monthUsed = monthUsed;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
