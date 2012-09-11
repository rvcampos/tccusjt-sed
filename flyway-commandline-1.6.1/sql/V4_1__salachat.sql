CREATE TABLE sala_chat
(
  id_sala bigint NOT NULL,
  id_modulo bigint,
  id_chat character varying(4),
  dias character varying(50),
  horario time without time zone,
  uri character varying(200),
  horario_termino time without time zone,
  CONSTRAINT sala_chat_pkey PRIMARY KEY (id_sala ),
  CONSTRAINT sala_chat_id_modulo_fkey FOREIGN KEY (id_modulo)
      REFERENCES modulo (id_modulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

  CREATE SEQUENCE seq_chat
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999999999999
  START 1
  CACHE 1;