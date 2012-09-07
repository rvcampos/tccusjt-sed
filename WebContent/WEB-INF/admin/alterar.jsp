<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Administrador</title>
</head>
<script>

function validaSenhaForm() {
	if ($('#txtSenha').val() != '' || $('#txtCsenha').val() != '') {
		if ($('#txtSenha').val() != $('#txtCsenha').val()) {
			alert('Confirmacao de senha invalida');
			return false;
		}
	}
	return true;
}

</script>
		<form class="form-horizontal" action="alterar" method="post"
	id="formAdmin">
	<legend>
		<b>Cadastro de Administrador </b>
	</legend>
	<div class="tabbable">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#1" data-toggle="tab">Dados
					principais</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="1">
				<div class="control-group">
					<label class="control-label" for="txtNome">Nome Completo </label>
					<div class="controls">
						<input type="text" id="txtNome" name="txtNome" value="${txtNome}"
							placeholder="Nome Completo" maxlength="50">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="txtEmail">Email</label>
					<div class="controls">
						<input type="text" id="txtEmail" name="txtEmail" value="${txtEmail}" maxlength="50" readonly="readonly"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtSenha">Senha</label>
					<div class="controls">
						<input type="password" id="txtSenha" name="txtSenha"
							placeholder="Senha" maxlength="8">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtCsenha">Confirma Senha</label>
					<div class="controls">
						<input type="password" id="txtCsenha" name="txtCsenha"
							placeholder="Confirmação de Senha" maxlength="8">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span2">
			<input class="btn btn-primary" value="Salvar" type="button"
				name="btnSalvar"
				onclick="if(validaSenhaForm()){$('#formAdmin').submit();}" />
		</div>

		<div class="span2">
			<a href="./read" class="btn">Cancelar</a>
		</div>

	</div>
</form>
