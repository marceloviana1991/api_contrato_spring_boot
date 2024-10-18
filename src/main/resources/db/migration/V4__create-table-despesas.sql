create table despesas (

    id bigint not null auto_increment,
    data date not null,
    valor integer not null,
    descricao varchar(250) not null,
    id_imovel bigint not null,
    foreign key (id_imovel) references imoveis(id),

    primary key(id)
);