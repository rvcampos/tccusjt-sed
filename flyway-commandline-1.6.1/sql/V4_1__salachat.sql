CREATE TABLE sala_chat
(
  id_sala bigint NOT NULL,
  id_modulo bigint,
  id_chat character varying(4),
  inicio_domingo time without time zone,
  termino_domingo time without time zone,
  inicio_segunda time without time zone,
  termino_segunda time without time zone,
  inicio_terca time without time zone,
  termino_terca time without time zone,
  inicio_quarta time without time zone,
  termino_quarta time without time zone,
  inicio_quinta time without time zone,
  termino_quinta time without time zone,
  inicio_sexta time without time zone,
  termino_sexta time without time zone,
  inicio_sabado time without time zone,
  termino_sabado time without time zone,
  uri character varying(200),
  CONSTRAINT sala_chat_pkey PRIMARY KEY (id_sala ),
  CONSTRAINT sala_chat_id_modulo_fkey FOREIGN KEY (id_modulo)
      REFERENCES modulo (id_modulo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

  CREATE SEQUENCE seq_chat
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9999999999999999
  START 1
  CACHE 1;