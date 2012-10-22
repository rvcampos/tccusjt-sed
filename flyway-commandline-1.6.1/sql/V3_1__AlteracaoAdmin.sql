ALTER TABLE administrador ADD CONSTRAINT uk_email_administrador UNIQUE (email);  
  

CREATE SEQUENCE seq_admin
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 99999999
  START 1
  CACHE 1;
  
  
INSERT INTO ADMINISTRADOR VALUES (1, 'TuIfvY7HR6k=', 'admin@ead.com', 'Administrador EAD')