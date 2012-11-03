<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${nomeCurso}</title>
<form action="sendMail" class="form-horizontal" method="post"
	id="formEmail">

	<div class="control-group">
		<label class="control-label" for="txtNome">De:</label>
		<div class="controls">
			<input class="span2" size="16" type="text" name="txtNome"
				value="${de}" readonly="readonly">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="txtNome">Para:</label>
		<div class="controls">
			<input class="span2" size="16" type="text" name="txtProfessor"
				value="${para}" readonly="readonly">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="txtAssunto">Assunto:</label>
		<div class="controls">
			<input class="span2" size="16" type="text" name="txtAssunto"
				value="${txtAssunto}">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="txtConteudo">Conteudo</label>
		<div class="controls">
			<textarea rows="15" cols="30" name="txtConteudo">${txtConteudo}</textarea>
		</div>
	</div>
	<input type="hidden" name="id_matricula" value="${id_matricula}" /> 
	<input type="hidden" name="id_curso" value="${id_curso}" /> 
	<input type="hidden" name="id_email" value="${id_email}" />
	<div class="row-fluid">
		<div class="span2">
			<input class="btn btn-primary" value="Enviar" type="submit"
				name="btnEnviar" />
		</div>

		<div class="span2">
			<a href="javascript:history.go(-1)" class="btn">Cancelar</a>
		</div>

	</div>
</form>
</head>
