create table contratos (

    id bigint not null auto_increment,
    data_inicio date not null,
    data_finalizacao date not null,
    mensalidade integer not null,
    iptu integer not null,
    id_imovel bigint not null,
    foreign key (id_imovel) references imoveis(id),

    primary key(id)
);