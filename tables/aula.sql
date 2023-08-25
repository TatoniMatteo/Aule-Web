create table aula
(
    id               int auto_increment
        primary key,
    nome             varchar(255)  not null,
    luogo            varchar(255)  not null,
    edificio         varchar(255)  not null,
    piano            tinyint       not null,
    capienza         int           not null,
    prese_elettriche int           null,
    prese_rete       int           null,
    note             text          null,
    id_responsabile  int           null,
    versione         int default 0 null,
    constraint aula_ibfk_1
        foreign key (id_responsabile) references responsabile (id)
            on update cascade
)
    charset = utf8mb4;

create index id_responsabile
    on aula (id_responsabile);

INSERT INTO auleweb.aula (id, nome, luogo, edificio, piano, capienza, prese_elettriche, prese_rete, note, id_responsabile, versione) VALUES (2, 'Aula 201', 'Via Roma, 1', 'Edificio A', 2, 40, 15, 8, 'Generiche note per l\'aula 201', 2, 0);
INSERT INTO auleweb.aula (id, nome, luogo, edificio, piano, capienza, prese_elettriche, prese_rete, note, id_responsabile, versione) VALUES (3, 'Aula 301', 'Via Milano, 2', 'Edificio B', 3, 30, 10, 6, 'Generiche note per l\'aula 301', 3, 0);
INSERT INTO auleweb.aula (id, nome, luogo, edificio, piano, capienza, prese_elettriche, prese_rete, note, id_responsabile, versione) VALUES (4, 'Aula 102', 'Via Roma, 1', 'Coppito 2', 1, 120, 60, 10, 'Aula in manutenzione', 1, 0);
