package com.univaq.project.auleweb.data.implementation.enumType;

public enum AttrezzaturaTipo {
    PROIETTORE("Proiettore"),
    SCHERMO_MOTORIZZATO("Schermo Motorizzato"),
    SCHERMO_MANUALE("Schermo Manuale"),
    IMAPIANTO_AUDIO("Imapianto Audio"),
    PC_FISSO("PC Fisso"),
    MICROFONO_WIRED("Microfono con Filo"),
    MICROFONO_WIRLESS("Microfono senza Filo"),
    LAVAGNA_LUMINOSA("Lavagna Luminosa"),
    LAVAGNA("Lavagna"),
    WIFI("Wi-Fi");

    private String name;

    AttrezzaturaTipo(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
