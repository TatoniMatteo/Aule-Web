DROP DATABASE IF EXISTS auleweb;

-- Create database
CREATE DATABASE IF NOT EXISTS auleweb;

-- Use database
USE auleweb;

CREATE TABLE Responsabile
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(255) NOT NULL,
    cognome        VARCHAR(255) NOT NULL,
    codice_fiscale VARCHAR(16)  NOT NULL,
    email          VARCHAR(255),
    versione       INT
);

CREATE TABLE Corso
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(255) NOT NULL,
    descrizione  TEXT,
    corso_laurea VARCHAR(255),
    versione     INT
);

-- Definizione delle tabelle
CREATE TABLE Aula
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    nome             VARCHAR(255) NOT NULL,
    luogo            VARCHAR(255) NOT NULL,
    edificio         VARCHAR(255) NOT NULL,
    piano            VARCHAR(255) NOT NULL,
    capienza         INT          NOT NULL,
    prese_elettriche INT,
    prese_rete       INT,
    note             TEXT,
    id_responsabile  INT,
    versione         INT,
    FOREIGN KEY (id_responsabile) REFERENCES RESPONSABILE (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Gruppo
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(255) NOT NULL,
    descrizione TEXT,
    versione    INT
);

CREATE TABLE Aula_Gruppo
(
    id_aula   INT NOT NULL,
    id_gruppo INT NOT NULL,
    versione  INT,
    PRIMARY KEY (id_aula, id_gruppo),
    FOREIGN KEY (id_aula) REFERENCES AULA (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_gruppo) REFERENCES GRUPPO (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Attrezzatura
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nome         VARCHAR(255) NOT NULL,
    numero_serie VARCHAR(255) NOT NULL,
    id_aula      INT,
    versione     INT,
    FOREIGN KEY (id_aula) REFERENCES AULA (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Evento
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    id_ricorrenza   INT,
    nome            VARCHAR(255) NOT NULL,
    descrizione     TEXT,
    data            DATE         NOT NULL,
    ora_inizio      TIME         NOT NULL,
    ora_fine        TIME         NOT NULL,
    id_corso        INT,
    id_responsabile INT,
    id_aula         INT,
    versione        INT,
    FOREIGN KEY (id_corso) REFERENCES CORSO (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_responsabile) REFERENCES RESPONSABILE (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_aula) REFERENCES AULA (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE Amministratore
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    versione INT
);


