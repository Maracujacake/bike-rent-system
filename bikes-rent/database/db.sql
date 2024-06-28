drop database if exists bikeRentSystem;

create database bikeRentSystem;

use bikeRentSystem;

create table cliente(
    id bigint not null auto_increment, 
    email varchar(100) not null, 
    senha varchar(255) not null, 
    nome varchar(255) not null, 
    telefone varchar(20) not null,
    sexo varchar(15) not null, 
    cpf varchar(15) not null unique, 
    dataNascimento date not null, primary key (id));

/* foreign key (editora_id) references Editora(id) */
create table locadora(
    id bigint not null auto_increment,
    email varchar(100) not null,
    senha varchar(250) not null,
    nome varchar(256) not null,
    cnpj varchar(15) not null unique,
    cidade varchar(100) not null,
    primary key (id));

/*id  cpf_cliente cnpj_editora dataLocacao */
create table locacao(
    id bigint not null auto_increment, 
    cpfCliente varchar(15) not null, 
    cnpjLocadora varchar(15) not null, 
    dataHorario varchar(50) not null, 
    primary key (id), 
    foreign key(cpfCliente) references cliente(cpf), 
    foreign key(cnpjLocadora) references locadora(cnpj)
);

create table admins(
    id bigint not null auto_increment,
    email varchar(100) not null,
    senha varchar(250) not null,
    nome varchar(256) not null,
    primary key (id)
);


quit