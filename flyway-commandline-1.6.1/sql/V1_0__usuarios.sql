-- Geração de Modelo físico
-- Sql ANSI 2003 - brModelo.

--Modelo de dados físico gerado para o Sistema de Ensino á Distância

-- drop table ADMINISTRADOR
CREATE TABLE ADMINISTRADOR (
id_admin integer PRIMARY KEY,
senha varchar(20),
email varchar(20),
idcontato integer
);

-- drop table MATRICULA
CREATE TABLE MATRICULA (
id_matricula integer PRIMARY KEY,
id_modulo integer,
id_aluno integer
);

-- drop table AVALIACAO
CREATE TABLE AVALIACAO (
id_avaliacao integer PRIMARY KEY,
id_modulo integer,
data_inicio date,
data_termino date
);

-- drop table MATERIAL_DIDATICO
CREATE TABLE MATERIAL DIDATICO (
endereco_material varchar(60) PRIMARY KEY,
id_modulo integer,
tipo_material integer
);

-- drop table DISCIPLINA
CREATE TABLE DISCIPLINA (
id_disciplina integer,
nome_disciplina varchar(100),
id_professor integer,
data_inicio date,
data_termino date,
PRIMARY KEY(id_disciplina,nome_disciplina)
);

-- drop table MODULO
CREATE TABLE MODULO (
id_modulo integer PRIMARY KEY,
id_disciplina integer,
nivel_modulo integer,
data_inicio date,
data_termino date
);

-- drop table ALUNO
CREATE TABLE ALUNO (
id_aluno integer PRIMARY KEY,
id_matricula integer,
id_contato integer,
senha varchar(20),
email varchar(30)
);

-- drop table PROFESSOR
CREATE TABLE PROFESSOR (
id_professor integer PRIMARY KEY,
id_contato integer,
email varchar(20),
senha varchar(46)
);

-- drop table CONTATO
CREATE TABLE CONTATO (
id_contato integer PRIMARY KEY,
data_nascimento Date,
nome varchar(100),
rg   varchar(12),
cpf  numeric(12));

-- drop table CONTATO
CREATE TABLE ENDERECO (
id_endereco integer PRIMARY KEY,
id_cidade   integer ,
bairro varchar(100),
endereco  varchar(120));

