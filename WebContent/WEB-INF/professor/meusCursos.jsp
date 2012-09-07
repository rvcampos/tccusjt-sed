<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cursos Ministrados</title>
</head>
<table class="table">
	<tr>
		<th></th>
		<th></th>
		<th>Nome</th>
		<th>Data Inicio</th>
		<th>Data Termino</th>
		<th>Descrição</th>
	</tr>
	<c:forEach var="curso" items="${cursos}">
		<tr>
			<td><form action="detalha" method="post" id="formMeusCursos${curso.id_disciplina}">
					<input type="hidden" value=${curso.id_disciplina} name="id_disciplina" />
					<a onclick="$('#formMeusCursos${curso.id_disciplina}').submit();"><i class="icon-pencil"></i></a>
				</form></td>
			<td><form action="deleteCurso" method="post" id="formMeusCursosDel${curso.id_disciplina}">
					<input type="hidden" value=${curso.id_disciplina} name="id_disciplina" />
					<a onclick="$('#formMeusCursosDel${curso.id_disciplina}').submit();"><i class="icon-remove"></i></a>
				</form></td>
			<td>${curso.nome_disciplina}</td>
			<td>${curso.data_inicio}</td>
			<td>${curso.data_termino}</td>
			<td>${curso.descricao}</td>
		</tr>
	</c:forEach>
</table>
