<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alterar Dados Professor</title>
</head>
<script>

function validaSenhaForm()
{
	if($('#txtSenha').val() != '' || $('#txtCsenha').val() != '')
	{
		alert('Preencha a senha e a validação');
		return false;
		if($('#txtSenha').val() != $('#txtCsenha').val())
		{
			alert('Confirmacao de senha invalida');
			return false;
		}
	}
	return true;
}

function changeUF(state) {
	return changeUF(state, null);
}
function changeUF(state, callback) {
	$.ajax({
		url : "${app_context}cidade/carrega_uf",
		type : "POST",
		cache : false,
		data : {
			"estado_id" : state
		},
		dataType : "text",
		success : function(msg) {
			$("#ufcidade").html(msg);
			$("#combobox_city").combobox();
				if (callback) {
				callback();
			}
		}
	});
}

</script>
		<form class="form-horizontal" action="alterar" method="post"
	id="formProfessor">
	<legend>
		<b>Cadastro de Professor </b>
	</legend>
	<div class="tabbable">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#1" data-toggle="tab">Dados
					principais</a></li>
			<li class=""><a href="#2" data-toggle="tab">Contato</a></li>
			<li class=""><a href="#3" data-toggle="tab">Endereco</a></li>
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
						<input type="text" id="txtEmail" name="txtEmail" value="${txtEmail}" readonly="readonly" maxlength="50" />
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
			<div class="tab-pane" id="2">
				<div class="control-group">
					<label class="control-label" for="txtTelefone">Telefone</label>
					<div class="controls">
						<input type="text" id="txtTelefoneDDD" name="txtTelefoneDDD"
							placeholder="xx" maxlength="2" style="width: 15px;" alt="99"
							value="${txtTelefoneDDD}"> <input type="text"
							id="txtTelefone" name="txtTelefone" placeholder="9999-9999"
							maxlength="9" alt="9999-9999" value="${txtTelefone}">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="txtTelefone">Celular</label>
					<div class="controls">
						<input type="text" id="txtCelularDDD" name="txtCelularDDD"
							placeholder="xx" maxlength="2" style="width: 15px;" alt="99"
							value="${txtCelularDDD}"> <input type="text"
							id="txtCelular" name="txtCelular" placeholder="9999-9999"
							maxlength="9" alt="9999-9999" value="${txtCelular}">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="txtRG">RG </label>
					<div class="controls">
						<input type="text" id="txtRG" name="txtRG" value="${txtRG}"
							placeholder="RG" maxlength="10">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="txtCPF">CPF </label>
					<div class="controls">
						<input type="text" id="txtCPF" name="txtCPF" value="${txtRG}"
							placeholder="CPF" alt="cpf">
					</div>
				</div>

				<div class="control-group">
					<label class="control-label" for="txtCPF"> Data de
						Nascimento</label>
					<div class="controls">
						<input type="text" id="txtNascimento" name="txtNascimento"
							value="${txtNascimento}" placeholder="Data de Nascimento"
							alt="39/19/2999">
					</div>
				</div>
			</div>
			<div class="tab-pane" id="3">

				<div class="control-group">
					<label class="control-label" for="txtEndereco">Endereço</label>
					<div class="controls">
						<input type="text" id="txtEndereco" name="txtEndereco"
							value="${txtEndereco}"
							placeholder="Logradouro, número, apto, bloco" maxlength="50">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtBairro">Bairro</label>
					<div class="controls">
						<input type="text" id="txtBairro" name="txtBairro"
							value="${txtBairro}" placeholder="Bairro" maxlength="50">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="CboUF">UF</label>
					<div class="controls">
						<select style="width: 60px;" id="CboUF" name="CboUF"
							onchange="changeUF($(this).val());">
							<option></option>
							<c:forEach var="estado" items="${lista_uf}">
								<option id="${estado.id_estado}" value="${estado.id_estado}"
									<c:if test="${estado.id_estado eq uf_id}">selected="selected"</c:if>>${estado.uf}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="CboUF">Cidade</label>
					<div class="controls" id="ufcidade">
						<div class="ui-widget" id="widget_combobox_city"
							style="float: left; margin-right: 10px">
							<select name="cidade" id="combobox_city">
								<option></option>
								<c:forEach var="cities" items="${list_city}">
									<c:set var="selected" value="" />
									<c:if test="${cidade eq cities.id_cidade}">
										<c:set var="selected" value="selected=\"selected\"" />
									</c:if>
									<option value="${cities.id_cidade}" id="${cities.id_cidade}"
										${selected}>${cities.nome}</option>
								</c:forEach>
							</select>
						</div>
						<script>
							$('#combobox_city').combobox();
						</script>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtCep">Cep</label>
					<div class="controls">
						<input type="text" id="txtCep" name="txtCep" value="${txtCep}"
							placeholder="99999-999" maxlength="8" alt ="99999-999" >
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span2">
			<input class="btn btn-primary" value="Salvar" type="button"
				name="btnSalvar"
				onclick="if(validaSenhaForm()){$('#formProfessor').submit();}" />
		</div>

		<div class="span2">
			<a href="./read" class="btn">Cancelar</a>
		</div>

	</div>
</form>
