/* comandos relativos a inicialização do banco de dados*/

drop database if exists bikeRentSystem;

create database bikeRentSystem;

use bikeRentSystem;

create table Cliente(
    id bigint not null auto_increment, 
    email varchar(100) not null, 
    senha varchar(250) not null, 
    nome varchar(256) not null, 
    telefone varchar(20) not null,
    sexo varchar(15) not null, 
    cpf varchar(15) not null unique, 
    dataNascimento date not null, primary key (id));

/* foreign key (editora_id) references Editora(id) */
create table Locadora(
    id bigint not null auto_increment,
    email varchar(100) not null,
    senha varchar(250) not null,
    nome varchar(256) not null,
    cnpj varchar(15) not null unique,
    cidade varchar(100) not null,
    primary key (id));

/*id  cpf_cliente cnpj_editora dataLocacao */
create table Locacao(
    id bigint not null auto_increment, 
    cpf_cliente varchar(15) not null, 
    cnpj_editora varchar(15) not null, 
    dataLocacao datetime not null, 
    primary key (id), 
    foreign key(cpf_cliente) references Cliente(cpf), 
    foreign key(cnpj_editora) references Locadora(cnpj)
    );


insert into Cliente(email, senha, nome, telefone, sexo, cpf, dataNascimento) values  ('sduhusad@saudhas', 'uasdhuash', 'carol', '123456789', 'feminino', '123456789', '2001-07-04');

quit