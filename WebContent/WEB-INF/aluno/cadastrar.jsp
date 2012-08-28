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
	if($('#txtSenha').val() != $('#txtCsenha').val())
	{
		alert('Confirmacao de senha invalida');
		return false;
	}
	return true;
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
			<li class="active"><a href="#1" data-toggle="tab">Dados cadastrais</a></li>  
			<li class=""><a href="#2" data-toggle="tab">Endereco</a></li>  
			<li class=""><a href="#3" data-toggle="tab">Usuario</a></li> 
			</ul>
			<div class="tab-content">  
			<div class="tab-pane active" id="1">  
			<table class="table">
				<tr>
					<td><label>Nome Completo </label></td>
					<td><input type="text" id="txtNome" name="txtNome" maxlength="50" size="50"></td>
				</tr>

				<tr>

					<td><label>RG</label></td>
					<td><input name="txtRG" id="txtRG" name="txtRG" maxlength="10" size="12"></td>
				</tr>

				<tr>
					<td><label>CPF</label></td>
					<td><input id="txtCPF" name="txtCPF" type="text" alt="cpf" maxlength="11" size="12"></td>
				</tr>

				<tr>
					<td><label> Sexo </label></td>
					<td><input type="radio" name="sexo" value="0" checked="checked">Masculino
						<input type="radio" name="sexo" value="1">Feminino</td>
				</tr>

				<tr>
					<td><label>Foto</label></td>
					<td><input type="file" id="foto" name="foto"></td>
				</tr>

				<tr>
					<td><label> Data de Nascimento </label></td>
					<td><input name="txtNascimento" id="txtNascimento" type='text' maxlength="10" size="12" alt="39/19/2999"></td>
				</tr>
				</table>
				</div>
				<div class="tab-pane" id="2">  
				<table  class="table">
				<tr>
					<td><label>Endereço</label></td>
					<td><input type='text' id="txtEndereco" name="txtEndereco" maxlength="50" size="50"></td>
				</tr>

				<tr>

					<td><label>Bairro</label></td>
					<td><input type='text' id="txtBairro" name="txtBairro" maxlength="50" size="50"></td>
				</tr>

				<tr>
					<td><label>UF</label></td>
					<td>
						<script>
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
						</script>
						<select style="width: 40px;" id="CboUF" name="CboUF" onchange="changeUF($(this).val());">
							<option></option>
							<c:forEach var="estado" items="${lista_uf}">
								<option id="${estado.id_estado}" <c:if test="${estado.id_estado eq uf_id}">selected="selected"</c:if>>${estado.uf}</option>							
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
									<c:if test="${cidade_id eq cities.id_cidade}">
										<c:set var="selected" value="selected=\"selected\"" />
									</c:if>
									<option value="${cities.id_cidade}" id="${cities.id_cidade}"
										${selected}>${cities.nome}</option>
								</c:forEach>
							</select>
						</div>
					</td>
				</tr>

				<tr>
					<td><label>Cep</label></td>
					<td><input type='text' id="txtCep" name="txtCep" alt='cep' maxlength="8" size="8"></td>
					</td>
				</tr>
				</table>
				</div>
				<div class="tab-pane" id="3">  
				<table class="table">
				<tr>
					<td><label>Telefone</label></td>
					<td><input type="text" id="txtTelefoneDDD" name="txtTelefoneDDD" maxlength="2" size="2" style="width:15px;" alt="99"> <input type="text" id="txtTelefone" name="txtTelefone" maxlength="9" alt="9999-9999"></td>
				</tr>

				<tr>
					<td><label>Celular</label></td>
					<td><input type="text" id="txtCelularDDD" name="txtCelularDDD" maxlength="2" size="2" style="width:15px;" alt="99"> <input type="text" id="txtCelular" name="txtCelular" maxlength="9" alt="9999-9999"></td>
				</tr>

				<tr>
					<td><label>Email</label></td>
					<td><input type="text" id="txtEmail" name="txtEmail" maxlength="50" size="50"></td>
				</tr>

				<tr>
					<td><label>Usuário</label></td>
					<td><input type="text" id="txtUsuario" name="txtUsuario" maxlength="20" size="20" value="${txtUsuario}"></td>
				</tr>

				<tr>
					<td><label>Senha</label></td>
					<td><input id="txtSenha" name="txtSenha" type="password" maxlength="8"
						size="8" value="${txtSenha}"></td>
				</tr>
				<tr>
					<td><label>Confirma Senha</label></td>
					<td><input id="txtCsenha" name="txtCsenha" type="password" maxlength="8"
						size="8"></td>
				</tr>
			</table>
			</div>
			</div>
			</div>
		</td>
		</tr>
		<tr>
		<td>
		<center>
			<button id="cmdIncluir" type="button" onclick="if(validaSenhaForm()){$('#formAluno').submit();}" style="width: 70px;">
				Incluir</button>
			<button id="cmdCancelar" type="button" style="width: 70px;">
				Cancelar</button>
		</center>
		</td>
		</tr>
		</table>
		</form>
