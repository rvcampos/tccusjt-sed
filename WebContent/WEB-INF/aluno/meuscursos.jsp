<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Meus Cursos</title>
</head>
<table class="table">
	<tr>
		<th>Disciplina</th>
		<th>Nivel</th>
		<th>Data de Inicio</th>
		<th>Data fim</th>
		<th>Professor</th>
		<th>Cursar</th>
		<th>Desmatricular</th>
	</tr>
	<c:forEach var="curso" items="${cursos}">
		<td>${curso.modulo.disciplina.nome_disciplina}</td>
		<td>${curso.modulo.nivel_modulo}</td>
		<td>${curso.modulo.dt_inicio}</td>
		<td>${curso.modulo.dt_fim}</td>
		<td>${curso.modulo.disciplina.professor.contato.nome}</td>
		<td></td>
		<td></td>
	</c:forEach>
</table>