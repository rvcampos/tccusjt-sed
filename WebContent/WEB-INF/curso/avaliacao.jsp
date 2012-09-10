<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Prova</title>
</head>

<form action="corrigeProva" class="form-horizontal" method="post"
	id="formProva">
	<legend>
		<b>Prova</b>
	</legend>

	<c:set var="countQuest" value="${1}" />
	<c:forEach var="quest" items="${modulo.avaliacao.questoes}">
		<input type="hidden" name="matricula" value="${matricula.id_matricula}"/>
		<table>
			<tr>
				<th>Questão ${countQuest} <b> - ${quest.conteudo} </th>
			</tr>
			<c:set var="countAlt" value="${1}" />
			<c:forEach var="alt" items="${quest.alternativas}">
				<tr>
					<td><label class="radio"> <input type="radio"
							name="questao${quest.id_questao}" id="questao${quest.id_questao}"
							value="${alt.id_alternativa}"> ${alt.conteudo}
					</label></td>
				</tr>
				<c:set var="countAlt" value="${countAlt + 1}" />
			</c:forEach>
			<c:set var="countQuest" value="${countQuest + 1}" />
	</c:forEach>
	</table>

	<div class="row-fluid">
		<div class="span2">
			<input class="btn btn-primary" value="Concluir" type="submit"
				name="btnSalvar" />
		</div>

		<div class="span2">
			<a href="${app_context}aluno/meusCursos" class="btn">Cancelar</a>
		</div>

	</div>
</form>