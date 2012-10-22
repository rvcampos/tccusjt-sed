-- Table: email_duvidas

-- DROP TABLE email_duvidas;

CREATE TABLE email_duvidas
(
  id_email bigint NOT NULL PRIMARY KEY,
  id_email_pai bigint NOT NULL references email_duvidas (id_email),
  id_matricula bigint NOT NULL references matricula (id_matricula),
  data date NOT NULL,
  titulo varchar(100) NOT NULL,
  conteudo varchar(400) NOT NULL,
  top_mail boolean default false,
  respondido boolean default false
);
  
  CREATE SEQUENCE seq_emails
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;