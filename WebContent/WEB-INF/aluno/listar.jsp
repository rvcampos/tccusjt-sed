<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Aluno</title>
</head>
<table class="table">
	<tr>
		<th></th>
		<th></th>
		<th>Nome</th>
		<th>Data de Nascimento</th>  
		<th>E-mail</th>
	</tr>
	<c:forEach var="aluno" items="${alunos}">
		<tr>
			<td><form action="detalha" method="post"
					id="formAluno${aluno.id_aluno}">
					<input type="hidden" value=${aluno.id_aluno } name="id_aluno" />
					<a onclick="$('#formAluno${aluno.id_aluno}').submit();"><i
						class="icon-pencil"></i></a>
				</form></td>

			<td><form action="delete" method="post" id="formAlunoDelete${aluno.id_aluno}">
					<input type="hidden" value=${aluno.id_aluno } name="id_aluno" />
					<a onclick="$('#formAlunoDelete${aluno.id_aluno}').submit();"><i class="icon-remove"></i></a>
				</form></td>
			<td>${aluno.contato.nome}</td>
			<td>${aluno.contato.data_nascimento}</td>
			<td>${aluno.email}</td>
		</tr>
	</c:forEach>
</table>
