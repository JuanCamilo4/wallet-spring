package com.siscode.wallet.app.utils;

public enum Constants {

    MONTH_ACCOUNT_FILTER("month"),
    DATE_ACCOUNT_FILTER("all"),

    ERROR_FILTER_ACCOUNT_USED("Debe inidicar un filtro apropiado"),
    ERROR_SAVE_ACCOUNT_STRANGER("Ah  ocurrido un error guardando la cuenta"),
    ERROR_UPDATE_NOT_FOUND_ACCOUNT("No existe la cuenta indicada");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
