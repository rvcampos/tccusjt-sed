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
		<th>Data</th>
		<th>Respondido?</th>
		<th>Detalhar</th>
		<th>Responder</th>
	</tr>
	
	<c:forEach var="email" items="${lista_emails}">
		<tr>
			<td>${email.titulo}</td>
			<td width="300px">${email.data}</td>
			<td width="100px"><c:choose><c:when test="${email.respondido}"><i class="icon-thumbs-up" title="Sim"></i></c:when><c:otherwise><i class="icon-thumbs-down"></i></c:otherwise></c:choose></td>
			<td width="100px"><form action="${app_context}curso/email/detalharEmail" id="frm_det${email.id_email}" method="post"><input type="hidden" name="id_email" value="${email.id_email}" /><a onclick="$('#frm_det${email.id_email}').submit();"><i class="icon-search"></i></a></form></td>
			<td width="100px"><i class="icon-inbox"></i></td>
		</tr>
	</c:forEach>
</table>
<div class="span2">
	<form method="post" action="${app_context}curso/email/criaEmail">
		<input type="hidden" name="id_matricula" value="${id_matricula }" />
		<input type="submit" class="btn" value="Novo E-mail" />
	</form>
</div>

<div class="span2">
	<a href="${app_context}curso/email/criaEmail" class="btn">Cancelar</a>
</div>
</head>
