<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Meus Cursos</title>
</head>
<table class="table">
	<tr class="header">
		<th>Disciplina</th>
		<th>Nivel</th>
		<th>Data de Inicio</th>
		<th>Data fim</th>
		<th>Professor</th>
		<th>Cursar</th>
		<th>Desmatricular</th>
	</tr>
	<c:forEach var="curso" items="${cursos}">
	<c:set var="status" value="error" />
	<c:if test="${curso.concluido eq true}"><c:set var="status" value="success" /></c:if>
	<tr class="status">
		<td>${curso.modulo.disciplina.nome_disciplina}</td>
		<td>${curso.modulo.nivel_modulo}</td>
		<td>${curso.modulo.data_inicio}</td>
		<td>${curso.modulo.data_termino}</td>
		<td>${curso.modulo.disciplina.professor.contato.nome}</td>
		<td><i class="icon-book"></i></td>
		<td><i class="icon-remove"></i></td>
	</tr>
	</c:forEach>
</table>