package com.univaq.project.auleweb.data.implementation.enumType;

public enum Laurea {
    INFORMATICA("Informatica"),
    INGEGNERIA_INFORMATICA("Ingegneria Informatica"),
    INGEGNERIA_MECCANICA("Ingegneria Meccanica"),
    BIOTECNOLOGIE("Biotecnologie"),
    MEDICINA("Medicina"),
    SCIENZE_DELLA_FORMAZIONE("Scienze della formazione");

    private String name;

    Laurea(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
