package com.siscode.wallet.app.infraestructure;

import com.mongodb.lang.Nullable;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document("account")
public class AccountEntity {

    @Id
    @Field("_id")
    private ObjectId id;
    @NonNull
    private String name;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private Integer numberUsed;
    @NonNull
    private Integer monthUsed;
    @NonNull
    private String createdAt;
    @Nullable
    private String updatedAt;
    @NonNull
    private String color;
    @NonNull
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

    public AccountEntity() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    @Nullable
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@Nullable String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
