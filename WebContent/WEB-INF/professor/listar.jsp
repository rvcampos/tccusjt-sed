<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listar Professor</title>
</head>

<form action="${app_context }professor/listar" method="post">
	<table class="table table-condensed">
		<tr>
			<td>Nome:</td>
			<td><input type="text" name="filtro_nome" value="${filtro_nome}" /></td>
		</tr>
		<tr>
			<td>E-mail:</td>
			<td><input type="text" name="filtro_email"
				value="${filtro_email}" /></td>
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
		<th></th>
		<th>Nome</th>  
		<th>Data de Nascimento</th>  
		<th>E-mail</th>
	</tr>
	<c:forEach var="prof" items="${profs}">
		<tr>
			<td><form action="detalha" method="post" id="formProfessor${prof.id_professor}">
					<input type="hidden" value=${prof.id_professor } name="id_prof" />
					<a onclick="$('#formProfessor${prof.id_professor}').submit();"><i class="icon-pencil"></i></a>
				</form></td>
			<td><form action="delete" method="post"
					id="formProfessorDelete${prof.id_professor}">
					<input type="hidden" value=${prof.id_professor } name="id_prof" />
					<a onclick="if (confirm('Deseja remover o professor selecionado?'))$('#formProfessorDelete${prof.id_professor}').submit();"><i
						class="icon-remove"></i></a>
				</form></td>
			<td>${prof.contato.nome}</td>
			<td>${prof.contato.data_nascimento}</td>
			<td>${prof.email}</td>
		</tr>
	</c:forEach>
</table>
