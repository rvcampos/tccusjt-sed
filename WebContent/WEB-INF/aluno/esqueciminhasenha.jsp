<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Esqueci Minha Senha - Aluno</title>
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
		<form class="form-horizontal" action="${app_context}aluno/esqueciminhasenha" method="post" id="formAluno">
		<legend>
				<b>Reenvio de senha </b>
			</legend>
		<div class="tabbable">
			<div class="tab-content">
			<div class="control-group">
					<label class="control-label">E-mail cadastrado </label>
								<div class="controls">
						<input type="text" id="txtEmail" name="txtEmail" value="${txtEmail}" maxlength="50">
			</div>
			</div>

			</div>
		</div>
		<div class="row-fluid">

				<div class="span2">
					<input class="btn btn-primary" value="Enviar" type="button" name="btnSalvar" onclick="if(validaEmailForm()){$('#formAluno').submit();}"/>
				</div>

				<div class="span2">
					<a href="${app_context}" class="btn">Cancelar</a>
				</div>

			</div>
		</form>
