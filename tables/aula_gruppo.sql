create table aula_gruppo
(
    id_aula   int           not null,
    id_gruppo int           not null,
    versione  int default 0 null,
    primary key (id_aula, id_gruppo),
    constraint aula_gruppo_ibfk_1
        foreign key (id_aula) references aula (id)
            on update cascade on delete cascade,
    constraint aula_gruppo_ibfk_2
        foreign key (id_gruppo) references gruppo (id)
            on update cascade on delete cascade
)
    charset = utf8mb4;

create index id_gruppo
    on aula_gruppo (id_gruppo);

INSERT INTO auleweb.aula_gruppo (id_aula, id_gruppo, versione) VALUES (2, 2, 0);
INSERT INTO auleweb.aula_gruppo (id_aula, id_gruppo, versione) VALUES (2, 5, 0);
INSERT INTO auleweb.aula_gruppo (id_aula, id_gruppo, versione) VALUES (3, 3, 0);
INSERT INTO auleweb.aula_gruppo (id_aula, id_gruppo, versione) VALUES (3, 6, 0);
INSERT INTO auleweb.aula_gruppo (id_aula, id_gruppo, versione) VALUES (4, 1, 0);
INSERT INTO auleweb.aula_gruppo (id_aula, id_gruppo, versione) VALUES (4, 5, 0);
