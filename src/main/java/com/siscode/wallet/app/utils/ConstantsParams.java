package com.siscode.wallet.app.utils;

public enum ConstantsParams {

    MONTH_ACCOUNT_FILTER("month"),
    DATE_ACCOUNT_FILTER("all"),
    PAYMENT_CREDIT_CARD("CREDIT_CARD"),
    PAYMENT_DEBT_CARD("DEBT_CARD"),
    PAYMENT_DEBT("DEBT"),
    PAYMENT_CASH("CASH"),
    TYPE_RECORD_EXPENSE("EXPENSE"),
    TYPE_RECORD_INCOME("INCOME"),
    FILTER_TYPE("type"),
    FILTER_ACCOUNT("account"),
    FILTER_CATEGORY("category"),
    FILTER_PAYMENT("payment");

    private final String value;

    ConstantsParams(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
