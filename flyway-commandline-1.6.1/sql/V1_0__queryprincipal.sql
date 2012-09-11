-- Geração de Modelo físico
-- Sql ANSI 2003 - brModelo.

--Modelo de dados físico gerado para o Sistema de Ensino á Distância


-- drop table ESTADO_UF
CREATE TABLE ESTADO_UF (
id_estado integer PRIMARY KEY,
uf varchar(100));

-- drop table CIDADE
CREATE TABLE CIDADE (
id_cidade integer PRIMARY KEY,
nome varchar(100),
id_estado  integer references ESTADO_UF(id_estado),
capital integer);


-- drop table CONTATO
CREATE TABLE CONTATO (
id_contato integer PRIMARY KEY,
data_nascimento Date,
nome varchar(100),
rg   varchar(12));

create table TP_TELEFONE
(
id_tipo integer PRIMARY KEY,
nome varchar(40)
);

insert into tp_telefone values(1,'Fixo');
insert into tp_telefone values(2,'Celular');
insert into tp_telefone values(3,'Fax');
insert into tp_telefone values(4,'Radio');


create table TELEFONES
(
 id_telefone integer PRIMARY KEY,
 id_contato integer references contato (id_contato) on delete cascade,
 tipo integer references tp_telefone (id_tipo),
 ddd integer,
 telefone numeric(9)
);

-- drop table ENDERECO
CREATE TABLE ENDERECO (
id_endereco integer PRIMARY KEY,
id_cidade   integer,
bairro varchar(100),
logradouro  varchar(120),
cep    integer);

CREATE TABLE PROFESSOR (
id_professor integer PRIMARY KEY,
id_contato integer REFERENCES CONTATO(id_contato),
id_endereco integer references ENDERECO(id_endereco),
email varchar(60),
senha varchar(100),
cpf  numeric(12) not null);

CREATE TABLE ADMINISTRADOR (
id_admin integer PRIMARY KEY,
senha varchar(100),
email varchar(100),
nome varchar(100)
);

CREATE TABLE DISCIPLINA (
id_disciplina integer,
nome_disciplina varchar(100),
id_professor integer references PROFESSOR(id_professor),
data_inicio date,
data_termino date,
descricao varchar(255),
PRIMARY KEY(id_disciplina)
);

CREATE TABLE MODULO (
id_modulo integer PRIMARY KEY,
id_disciplina integer references DISCIPLINA(id_disciplina) on delete cascade,
nivel_modulo integer,
data_inicio date,
data_termino date
);
CREATE TABLE AVALIACAO (
id_avaliacao integer PRIMARY KEY,
id_modulo integer REFERENCES MODULO(id_modulo) on delete cascade,
data_inicio date,
data_termino date
);

CREATE TABLE MATERIAL_DIDATICO (
endereco_material varchar(60) PRIMARY KEY,
id_modulo integer REFERENCES MODULO(id_modulo) on delete cascade,
tipo_material integer
);

CREATE TABLE ALUNO (
id_aluno integer PRIMARY KEY,
id_contato integer REFERENCES contato(id_contato),
id_endereco integer references ENDERECO(id_endereco),
senha varchar(100),
email varchar(60),
cpf  numeric(12)
);

CREATE TABLE MATRICULA (
id_matricula bigint PRIMARY KEY,
id_modulo integer REFERENCES MODULO(id_modulo) on delete cascade,
id_aluno integer REFERENCES ALUNO(id_aluno) on delete cascade,
concluido boolean default false
);

CREATE SEQUENCE seq_aluno
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE seq_contato
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;

  CREATE SEQUENCE seq_endereco
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE seq_telefone
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE seq_professor
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE seq_disciplina
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE seq_matricula
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999999999999
  START 1
  CACHE 1;