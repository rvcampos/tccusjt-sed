-- drop table tb_tpusuario
create table tb_tpusuario
(
tpusuario_id bigint PRIMARY KEY,
nome varchar(20)
);

-- drop table tb_usuario
create table tb_usuario
(
usuario_id bigint PRIMARY KEY,
login varchar(30),
password varchar(100),
dt_ultimologin timestamp without time zone,
dt_trocapassword timestamp without time zone,
tp_usuario bigint references tb_tpusuario (tpusuario_id),
contato bigint
);

-- drop table tb_perfil
create table tb_perfil
(
perfil_id bigint PRIMARY KEY,
nome varchar(30)
);

-- drop table tb_perfil
create table tb_privilegio
(
privilegio_id bigint PRIMARY KEY,
tp_privilegio varchar(30),
nome varchar(50)
);

-- drop table tb_recurso
create table tb_recurso
(
recurso_id bigint PRIMARY KEY,
url varchar(30),
verb varchar(30),
nome varchar(50)
);

-- drop table tb_permissao
create table tb_permissao
(
permissao_id bigint PRIMARY KEY,
recurso_id bigint references tb_recurso (recurso_id),
privilegio_id bigint references tb_privilegio (privilegio_id),
nome varchar(50)
);

-- drop table tb_rel_perfil_permissao
create table tb_rel_perfil_permissao
(
perfil_id bigint references tb_perfil (perfil_id) on delete cascade,
permissao_id bigint references tb_permissao (permissao_id) on delete cascade
);

-- drop table tb_rel_usuario_perfil
create table tb_rel_usuario_perfil
(
usuario_id bigint references tb_usuario (usuario_id) on delete cascade,
perfil_id bigint references tb_perfil (perfil_id) on delete cascade
);