create table imoveis(

    id bigint not null auto_increment,
    tipo_imovel varchar(50) not null,
    bairro varchar(100) not null,
    rua varchar(100) not null,
    numero varchar(20) not null,
    cidade varchar(100) not null,
    estado varchar(100) not null,
    cep varchar(20) not null,

    primary key(id)

);