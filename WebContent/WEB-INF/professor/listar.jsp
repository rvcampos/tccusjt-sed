<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Professor</title>
</head>
<table class="table">
	<tr>
		<th></th>
		<th>Nome</th>
		<th>E-mail</th>
	</tr>
	<c:forEach var="prof" items="${profs}">
		<tr>
			<td><form action="detalha" method="post" id="formProfessor${prof.id_professor}">
					<input type="hidden" value=${prof.id_professor } name="id_prof" />
					<a onclick="$('#formProfessor${prof.id_professor}').submit();"><i class="icon-pencil"></i></a>
				</form></td>
			<td>${prof.cpf}</td>
			<td>${prof.email}</td>
		</tr>
	</c:forEach>
</table>
