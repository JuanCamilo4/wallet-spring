package com.siscode.wallet.app.utils;

public enum ConstantsErrors {
    ERROR_FILTER_ACCOUNT_USED("Debe inidicar un filtro apropiado"),
    ERROR_SAVE_ACCOUNT_STRANGER("Ah  ocurrido un error guardando la cuenta"),
    ERROR_UPDATE_NOT_FOUND_ACCOUNT("No existe la cuenta indicada"),
    ERROR_SAVE_RECORD("Ocurrio un error guardando el registro"),
    ERROR_DISCOUNT_CREDIT_CARD("El monto es mayor que el disponible"),
    ERROR_MISSING_CREDIT_ACCOUNT("La tarjeta cuenta no existe");

    private final String value;

    ConstantsErrors(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
