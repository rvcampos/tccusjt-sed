<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bloqueios</title>
</head>

<form action="${app_context }admin/listarBloqueios" method="post">
	<table class="table table-condensed">
		<tr>
			<td>Aluno:</td>
			<td><input type="text" name="filtro_nome" value="${filtro_nome}" /></td>
		</tr>
		<tr>
			<td>Disciplina:</td>
			<td><input type="text" name="filtro_disciplina"
				value="${filtro_disciplina}" /></td>
		</tr>
		<tr>
			<td colspan="2"><input type="submit" name="btnFiltro"
				value="Pesquisar" class="btn btn-primary"/></td>
		</tr>
	</table>
	<br><br>
	<jsp:include page="../paginacao.jsp" />
</form>
<table class="table">
	<tr>
		<th></th>
		<th>Aluno</th>
		<th>Disciplina</th>
	</tr>
	<c:forEach var="bloqueio" items="${bloqueios}">
		<tr>
			<td><form action="delete" method="post"
					id="formDelete${bloqueio.id_bloqueio}">
					<input type="hidden" value=${bloqueio.id_bloqueio } name="id_bloqueio" />
					<a onclick="$('#formDelete${bloqueio.id_bloqueio}').submit();"><i
						class="icon-remove"></i></a>
				</form></td>
			<td>${bloqueio.aluno.contato.nome}</td>
			<td>${bloqueio.disciplina.nome_disciplina}</td>
		</tr>
	</c:forEach>
</table>
