package com.univaq.project.auleweb.data.implementation.enumType;

public enum Tipo {
    LEZIONE("Lezione"),
    ESAME("Esame"),
    SEMINARIO("Seminario"),
    PARZIALE("Parziale"),
    RIUNIONE("Riunione"),
    LAUREA("Laurea"),
    ALTRO("Altro");

    private final String name;

    Tipo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
