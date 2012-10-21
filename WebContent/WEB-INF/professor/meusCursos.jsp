<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cursos Ministrados</title>
</head>
<table class="table">
	<tr>
		<th></th>
		<th></th>
		<th>Materiais</th>
		<th>Nome</th>
		<th>Data Inicio</th>
		<th>Data Termino</th>
		<th>Descrição</th>
	</tr>
	<c:forEach var="curso" items="${cursos}">
		<tr>
			<td><form action="${app_context}curso/editar" method="post" id="formMeusCursos${curso.id_disciplina}">
					<input type="hidden" value=${curso.id_disciplina} name="id_disciplina" />
					<a onclick="$('#formMeusCursos${curso.id_disciplina}').submit();"><i class="icon-pencil"></i></a>
				</form></td>
			<td><form action="${app_context}curso/delete" method="post" id="formMeusCursosDel${curso.id_disciplina}">
					<input type="hidden" value="${curso.id_disciplina}" name="id_disciplina" />
					<a onclick="if(confirm('Deseja deletar esse curso?')){$('#formMeusCursosDel${curso.id_disciplina}').submit();}"><i class="icon-remove"></i></a>
				</form></td>
				<td><form action="${app_context}curso/materiais" method="post" id="formMeusCursosMateriais${curso.id_disciplina}">
					<input type="hidden" value="${curso.id_disciplina}" name="id_disciplina" />
					<a onclick="$('#formMeusCursosMateriais${curso.id_disciplina}').submit();"><i class="icon-upload"></i></a>
				</form></td>
			<td>${curso.nome_disciplina}</td>
			<td><f:formatDate value="${curso.data_inicio}" pattern="dd/MM/yyyy"/></td>
			<td><f:formatDate value="${curso.data_termino}" pattern="dd/MM/yyyy"/></td>
			<td>${curso.descricao}</td>
		</tr>
	</c:forEach>
</table>