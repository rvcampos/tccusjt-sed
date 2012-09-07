CREATE TABLE questao
(
  id_questao bigint NOT NULL PRIMARY KEY,
  conteudo character varying(300) NOT NULL,
  id_avaliacao integer NOT NULL REFERENCES avaliacao (id_avaliacao)
);

CREATE TABLE alternativa
(
  id_alternativa bigint NOT NULL PRIMARY KEY,
  conteudo character varying(300) NOT NULL,
  correta boolean default false,
  id_questao bigint NOT NULL REFERENCES questao (id_questao)
);


  CREATE SEQUENCE seq_questao
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  CREATE SEQUENCE seq_alternativa
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  
  CREATE SEQUENCE seq_avaliacao
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;