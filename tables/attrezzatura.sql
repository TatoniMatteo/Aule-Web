create table attrezzatura
(
    id           int auto_increment
        primary key,
    nome         varchar(255)  not null,
    numero_serie varchar(255)  not null,
    id_aula      int           null,
    versione     int default 0 null,
    constraint attrezzatura_ibfk_1
        foreign key (id_aula) references aula (id)
            on update cascade on delete cascade
)
    charset = utf8mb4;

create index id_aula
    on attrezzatura (id_aula);

INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (1, 'Proiettore', 'P12345', 4, 2);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (2, 'Lavagna', 'L54321', 2, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (3, 'Computer', 'C67890', 3, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (9, 'Proiettore', 'PR456', 2, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (11, 'Microfono', 'MI111', 4, 2);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (12, 'Microfono', 'MI222', null, 1);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (13, 'Microfono', 'MS333', null, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (14, 'Microfono', 'MX444', 3, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (15, 'Computer', 'CO555', 2, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (18, 'Lavagna', 'LB888', null, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (20, 'Lavagna', 'LD000', 2, 0);
INSERT INTO auleweb.attrezzatura (id, nome, numero_serie, id_aula, versione) VALUES (22, 'Microfono con Filo', 'M45678', null, 0);
