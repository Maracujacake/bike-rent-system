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

create table admin(
    id bigint not null auto_increment,
    email varchar(100) not null,
    senha varchar(250) not null,
    primary key (id)
);

-- Insert examples for table 'cliente'
insert into cliente (email, senha, nome, telefone, sexo, cpf, dataNascimento) values
('john@example.com', 'password123', 'John Doe', '1234567890', 'Male', '123456789', '1990-01-01'),
('jane@example.com', 'password456', 'Jane Smith', '9876543210', 'Female', '987654321', '1995-05-10'),
('bob@example.com', 'password789', 'Bob Johnson', '5555555555', 'Male', '555555555', '1985-12-25');

-- Insert examples for table 'locadora'
insert into locadora (email, senha, nome, cnpj, cidade) values
('rental1@example.com', 'rental123', 'Rental Company 1', '123456789', 'New York'),
('rental2@example.com', 'rental456', 'Rental Company 2', '987654321', 'Los Angeles'),
('rental3@example.com', 'rental789', 'Rental Company 3', '555555555', 'Chicago');

-- Insert examples for table 'admins'
insert into admin (email, senha) values
('admin1@example.com', 'admin123'),
('admin2@example.com', 'admin456'),
('admin3@example.com', 'admin789');


quit