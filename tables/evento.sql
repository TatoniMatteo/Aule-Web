create table evento
(
    id              int auto_increment
        primary key,
    id_ricorrenza   int           null,
    nome            varchar(255)  not null,
    descrizione     text          null,
    data            date          not null,
    ora_inizio      time          not null,
    ora_fine        time          not null,
    id_corso        int           null,
    id_responsabile int           null,
    id_aula         int           null,
    versione        int default 0 null,
    tipo_evento     varchar(30)   null,
    constraint evento_ibfk_1
        foreign key (id_corso) references corso (id)
            on update cascade,
    constraint evento_ibfk_2
        foreign key (id_responsabile) references responsabile (id)
            on update cascade,
    constraint evento_ibfk_3
        foreign key (id_aula) references aula (id)
            on update cascade,
    constraint check_start_end_time
        check (`ora_inizio` < `ora_fine`)
)
    charset = utf8mb4;

create index id_aula
    on evento (id_aula);

create index id_corso
    on evento (id_corso);

create index id_responsabile
    on evento (id_responsabile);

INSERT INTO auleweb.evento (id, id_ricorrenza, nome, descrizione, data, ora_inizio, ora_fine, id_corso, id_responsabile, id_aula, versione, tipo_evento) VALUES (2, null, 'Seminario Finanza', 'Seminario sulla finanza aziendale', '2023-08-22', '14:00:00', '16:00:00', null, 2, 2, 0, 'SEMINARIO');
INSERT INTO auleweb.evento (id, id_ricorrenza, nome, descrizione, data, ora_inizio, ora_fine, id_corso, id_responsabile, id_aula, versione, tipo_evento) VALUES (3, null, 'Lab Elettronica', 'Laboratorio di elettronica', '2023-08-22', '10:00:00', '13:00:00', 3, 3, 3, 0, 'LEZIONE');
