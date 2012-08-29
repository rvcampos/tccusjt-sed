ALTER TABLE aluno ADD COLUMN ativo boolean;
ALTER TABLE aluno ALTER COLUMN ativo SET DEFAULT false;
ALTER TABLE aluno ADD CONSTRAINT uk_email_aluno UNIQUE (email);


CREATE TABLE properties
(
  id_properties integer NOT NULL,
  nome character varying(50),
  valor character varying(50),
  CONSTRAINT properties_pkey PRIMARY KEY (id_properties),
  CONSTRAINT properties_nome_key UNIQUE (nome)
);

insert into properties(1,'email','admin@syscom.ead.nom.br');
insert into properties(2,'senha','62621741admin');
insert into properties(3,'smtp','smtp.gmail.com');
insert into properties(4,'portaSmtp','587');