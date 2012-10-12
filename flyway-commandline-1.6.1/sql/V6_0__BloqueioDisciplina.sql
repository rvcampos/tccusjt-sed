-- Table: bloqueio_disciplina

-- DROP TABLE bloqueio_disciplina;

CREATE TABLE bloqueio_disciplina
(
  id_aluno integer NOT NULL,
  id_disciplina integer NOT NULL,
  id_bloqueio integer NOT NULL,
  CONSTRAINT pk_bloqueio PRIMARY KEY (id_bloqueio ),
  CONSTRAINT fk_aluno FOREIGN KEY (id_aluno)
      REFERENCES aluno (id_aluno) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_disciplina FOREIGN KEY (id_disciplina)
      REFERENCES disciplina (id_disciplina) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_bloqueio UNIQUE (id_aluno , id_disciplina )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE bloqueio_disciplina
  OWNER TO postgres;
  
  
  
  CREATE SEQUENCE seq_bloqueio
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;

