alter table matricula add column dt_matricula date DEFAULT ('now'::text)::date;
alter table matricula add column dt_termino date;