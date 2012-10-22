<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Esquecinha Minha Senha - Aluno</title>
</head>
<script>

function validaEmailForm()
{
	if($('#txtEmail').val() == '')
	{
		alert('Preencha o email cadastrado');
		return false;
	}
	return true;
}

</script>
		<form action="${app_context}professor/esqueciminhasenha" method="post" id="formProfessor">
			<legend>
				<b>Reenvio de senha </b>
			</legend>
		<table>
		<tr>
		<td>
			<table class="table">
				<tr>
					<td><label>E-mail cadastrado </label></td>
					<td><input type="text" id="txtEmail" name="txtEmail" value="${txtEmail}" maxlength="50"></td>
				</tr>
		</table>
		<div class="row-fluid">

				<div class="span2">
					<input class="btn btn-primary" value="Enviar" type="button" name="btnSalvar" onclick="if(validaEmailForm()){$('#formProfessor').submit();}"/>
				</div>

				<div class="span2">
					<a href="./read" class="btn">Cancelar</a>
				</div>

			</div>
		</form>
