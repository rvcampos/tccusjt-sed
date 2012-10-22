<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<th>Emitir Certificado</th>
	</tr>
	<c:forEach var="curso" items="${cursos}">
	<c:set var="status" value="error" />
	<c:if test="${curso.certificado eq true or curso.concluido eq false}">
		<tr>
			<td>${curso.modulo.disciplina.nome_disciplina}</td>
			<td>${curso.modulo.nivel_modulo}</td>
			<td><f:formatDate value="${curso.modulo.data_inicio}" pattern="dd/MM/yyyy"/></td>
			<td><f:formatDate value="${curso.modulo.data_termino}" pattern="dd/MM/yyyy"/></td>
			<td>${curso.modulo.disciplina.professor.contato.nome}</td>
			<td><c:if test="${not curso.certificado}"><form action="${app_context}aluno/cursar" method="POST" id="frmCursar${curso.id_matricula}">
			<input type="hidden" name="id_matricula" value="${curso.id_matricula }"/>
			<a onclick="$('#frmCursar${curso.id_matricula}').submit();"><i class="icon-book"></i></a></form></c:if></td>
			<td><c:if test="${not curso.certificado}"><form action="${app_context}aluno/desmatricular" method="POST" id="frmDisciplina${curso.modulo.disciplina.id_disciplina}">
			<input type="hidden" name="id_disciplina" value="${curso.modulo.disciplina.id_disciplina }"/>
			<a onclick="if(confirm('Deseja se desmatricular do curso selecionado?'))$('#frmDisciplina${curso.modulo.disciplina.id_disciplina}').submit();"><i class="icon-remove"></i></a></form></c:if></td>
			<td><c:if test="${curso.certificado}"><form action="${app_context}aluno/certificado" method="POST" id="frmCertificado${curso.modulo.disciplina.id_disciplina}">
			<input type="hidden" name="id_disciplina" value="${curso.modulo.disciplina.id_disciplina }"/>
			<a onclick="$('#frmCertificado${curso.modulo.disciplina.id_disciplina}').submit();"><i class="icon-print"></i></a></form></c:if></td>
		</tr>
	</c:if>
	</c:forEach>
</table>