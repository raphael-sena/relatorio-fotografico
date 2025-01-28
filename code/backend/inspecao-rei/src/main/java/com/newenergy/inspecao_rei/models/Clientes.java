package com.newenergy.inspecao_rei.models;

public enum Clientes {
    CEMIG("CEMIG DISTRIBUIÇÃO S.A");

    private final String displayName;

    Clientes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
