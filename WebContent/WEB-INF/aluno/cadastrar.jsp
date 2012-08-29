<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Aluno</title>
</head>
<script>

function validaSenhaForm()
{
	if($('#txtSenha').val() == '' || $('#txtCsenha').val() == '')
	{
		alert('Preencha a senha e a validação');
		return false;
	}
	if($('#txtSenha').val() != $('#txtCsenha').val())
	{
		alert('Confirmacao de senha invalida');
		return false;
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
			$("#ufcidade").append('<span class="label label-important">Obrigatório</span>');
			if (callback) {
				callback();
			}
		}
	});
}

function validaEmail() {
	$.ajax({
		url : "${app_context}aluno/checaEmail",
		type : "POST",
		cache : false,
		data : {
			"email" : $('#txtEmail').val()
		},
		dataType : "text",
		success : function(msg) {
			$("#infoEmail").html(msg);
		}
	});
}
</script>
		<form action="create" method="post" id="formAluno">
			<legend>
				<b>Cadastro de Aluno </b>
			</legend>
		<table>
		<tr>
		<td>
			<div class="tabbable">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#1" data-toggle="tab">Dados
								cadastrais</a></li>
						<li class=""><a href="#2" data-toggle="tab">Usuario</a></li>
						<li class=""><a href="#3" data-toggle="tab">Endereco</a></li>
					</ul>
					<div class="tab-content">  
			<div class="tab-pane active" id="1">  
			<table class="table">
				<tr>
					<td><label>Nome Completo </label></td>
					<td><input type="text" id="txtNome" name="txtNome" value="${txtNome}" maxlength="50"></td>
				</tr>

				<tr>

					<td><label>RG</label></td>
					<td><input name="txtRG" id="txtRG" name="txtRG" value="${txtRG}" maxlength="10"></td>
				</tr>

				<tr>
					<td><label>CPF</label></td>
					<td><input id="txtCPF" name="txtCPF" type="text" alt="cpf" value="${txtCPF}" maxlength="11"></td>
				</tr>

				<tr>
					<td><label> Sexo </label></td>
					<td><input type="radio" name="sexo" value="0" checked="checked">Masculino
						<input type="radio" name="sexo" value="1">Feminino</td>
				</tr>

				<tr>
					<td><label> Data de Nascimento </label></td>
					<td><input name="txtNascimento" id="txtNascimento" value="${txtNascimento}" type='text' maxlength="10" alt="39/19/2999"></td>
				</tr>
				</table>
				</div>
				<div class="tab-pane" id="2">  
				<table class="table">
				<tr>
					<td><label>Telefone</label></td>
					<td><input type="text" id="txtTelefoneDDD" name="txtTelefoneDDD" value="${txtTelefoneDDD}" maxlength="2" style="width:15px;" alt="99" > <input type="text" id="txtTelefone" name="txtTelefone" value="${txtTelefone}" maxlength="9" alt="9999-9999"></td>
				</tr>

				<tr>
					<td><label>Celular</label></td>
					<td><input type="text" id="txtCelularDDD" name="txtCelularDDD" value="${txtCelularDDD}" maxlength="2" style="width:15px;" alt="99"> <input type="text" id="txtCelular" name="txtCelular" maxlength="9" alt="9999-9999" value="${txtCelular}"></td>
				</tr>

				<tr>
					<td><label>Email</label></td>
					<td><input type="text" id="txtEmail" name="txtEmail" value="${txtEmail}" maxlength="50" onchange="validaEmail();"><span id="infoEmail"></span></td>
				</tr>
				<tr>
					<td><label>Senha</label></td>
					<td><input id="txtSenha" name="txtSenha" type="password" maxlength="8" value="${txtSenha}"></td>
				</tr>
				<tr>
					<td><label>Confirma Senha</label></td>
					<td><input id="txtCsenha" name="txtCsenha" type="password" maxlength="8"></td>
				</tr>
				<tr></tr>
			</table>
			</div>
			<div class="tab-pane" id="3">  
				<table  class="table">
				<tr>
					<td><label>Endereço</label></td>
					<td><input type='text' id="txtEndereco" name="txtEndereco" value="${txtEndereco}" maxlength="50"></td>
				</tr>

				<tr>

					<td><label>Bairro</label></td>
					<td><input type='text' id="txtBairro" name="txtBairro" value="${txtBairro}" maxlength="50"></td>
				</tr>

				<tr>
					<td><label>UF</label></td>
					<td>
						<select style="width: 60px;" id="CboUF" name="CboUF" onchange="changeUF($(this).val());">
							<option></option>
							<c:forEach var="estado" items="${lista_uf}">
								<option id="${estado.id_estado}" value="${estado.id_estado}" <c:if test="${estado.id_estado eq uf_id}">selected="selected"</c:if>>${estado.uf}</option>							
							</c:forEach>					
						</select>
					</td>
				</tr>
				<tr>
					<td><label>Cidade</label></td>
					<td id="ufcidade">
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
										${selected}>${cities.nome}
									</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td><label>Cep</label></td>
					<td><input type='text' id="txtCep" name="txtCep" alt='cep' value="${txtCep}" maxlength="8" size="8"></td>
				</tr>
				</table>
				</div>
			</div>
			</div>
		</td>
		</tr>
		</table>
		<div class="row-fluid">

				<div class="span2">
					<input class="btn btn-primary" value="Salvar" type="button" name="btnSalvar" onclick="if(validaSenhaForm()){$('#formAluno').submit();}"/>
				</div>

				<div class="span2">
					<a href="./read" class="btn">Cancelar</a>
				</div>

			</div>
		</form>
