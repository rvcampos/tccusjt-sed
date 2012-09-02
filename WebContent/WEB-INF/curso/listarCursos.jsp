<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cursos</title>
</head>
<table class="table">
	<tr>
		<th>Disciplina</th>
		<th>Data de Inicio</th>
		<th>Data fim</th>
		<th>Professor</th>
		<th>Matricular</th>
	</tr>
	<c:forEach var="disciplina" items="${disciplinas}">
	<tr title="${disciplina.descricao}">
		<td>${disciplina.nome_disciplina}</td>
		<td>${disciplina.data_inicio}</td>
		<td>${disciplina.data_termino}</td>
		<td>${disciplina.professor.contato.nome}</td>
		<td></td>
	</tr>
	</c:forEach>
</table>