<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${nomeCurso}</title>
<table class="table">
	<tr>
		<th>Assunto</th>
		<c:if test="${ent == 1}">
			<th>Curso / Modulo</th>
		</c:if>
		<th>Data</th>
		<th>Respondido?</th>
		<th>Detalhar</th>
		<th>Responder</th>
	</tr>

	<c:forEach var="email" items="${lista_emails}">
		<tr>
			<td>${email.titulo}</td>
			<c:if test="${ent == 1}">
				<td>${email.matricula.modulo.disciplina.nome_disciplina} /
					${email.matricula.modulo.nivel_string}</td>
			</c:if>
			<td width="300px">${email.data}</td>
			<td width="100px"><c:choose>
					<c:when test="${email.respondido}">
						<i class="icon-thumbs-up" title="Sim"></i>
					</c:when>
					<c:otherwise>
						<i class="icon-thumbs-down"></i>
					</c:otherwise>
				</c:choose></td>
			<td width="100px"><form
					action="${app_context}curso/email/detalharEmail"
					id="frm_det${email.id_email}" method="post">
					<input type="hidden" name="id_email" value="${email.id_email}" />
					<a onclick="$('#frm_det${email.id_email}').submit();"><i
						class="icon-search"></i></a>
				</form></td>
			<td width="100px">
				<form action="${app_context}curso/email/criaEmail" id="frm_resp${email.id_email}" method="post">
					<c:if test="${ent == 1 and not email.respondido}">
						<input type="hidden" name="id_email" value="${email.id_email}" />
						<a onclick="$('#frm_resp${email.id_email}').submit();"><i class="icon-inbox"></i></a>
					</c:if>
					<c:if test="${ent == 2 and email.respondido}">
					<input type="hidden" name="id_matricula" value="${email.matricula.id_matricula}" />
					<input type="hidden" name="id_email" value="${email.id_email}" />
						<a onclick="$('#frm_resp${email.id_email}').submit();"><i class="icon-inbox"></i></a>
					</c:if>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>
<c:if test="${ent ==2}">
	<div class="span2">
		<form method="post" action="${app_context}curso/email/criaEmail">
			<input type="hidden" name="id_matricula" value="${id_matricula }" />
			<input type="submit" class="btn" value="Novo E-mail" />
		</form>
	</div>
</c:if>

<div class="span2">
	<a href="${app_context}login" class="btn">Cancelar</a>
</div>
</head>
