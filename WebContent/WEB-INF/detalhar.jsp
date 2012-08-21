<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Aluno</title>
</head>
		<fieldset style="width: 14cm;">
			<legend>
				<b>Cadastro de Aluno </b>
			</legend>
			<form action="create" method="post">
			<table class="table">
				<tr>
					<td><label>Nome Completo </label></td>
					<td><input type="text" id="txtNome" maxlength="50" size="50"></td>
				</tr>

				<tr>

					<td><label>RG</label></td>
					<td><input id="txtRG" maxlength="10" size="12"></td>
				</tr>

				<tr>
					<td><label>CPF</label></td>
					<td><input id="txtCPF" type="text" alt="cpf" maxlength="11" size="12"></td>
				</tr>

				<tr>
					<td><label> Sexo </label></td>
					<td><input type="radio" name="sexo" value="Masculino">Masculino
						<input type="radio" name="sexo" value="Feminino">Feminino</td>
				</tr>

				<tr>
					<td><label>Foto</label></td>
					<td><input id="txtFoto" size="25">
						<button id="cmdFoto" style="width: 120px;" type="submit">Escolher
							Arquivo</button></td>
				</tr>

				<tr>
					<td><label> Data de Nascimento </label></td>
					<td><input id="txtNascimento" maxlength="10" size="12"></td>
				</tr>

				<tr>
					<td><label>Endereço</label></td>
					<td><input id="txtEndereco" maxlength="50" size="50"></td>
				</tr>

				<tr>

					<td><label>Bairro</label></td>
					<td><input id="txtBairro" maxlength="50" size="50"></td>
				</tr>

				<tr>
					<td><label>UF</label></td>
					<td><select style="width: 40px;" id="CboUF"></select></td>
				</tr>
				<tr>
					<td><label>Cidade</label></td>
					<td><input id="txtCidade" maxlength="20" size="20">
				</tr>

				<tr>
					<td><label>Cep</label></td>
					<td><input id="txtCep" maxlength="8" size="8"></td>
					</td>
				</tr>

				<tr>
					<td><label>Telefone</label></td>
					<td><input id="txtTelefone" maxlength="11" size="12"></td>
				</tr>

				<tr>
					<td><label>Celular</label></td>
					<td><input id="txtCelular" maxlength="11" size="12"></td>
				</tr>

				<tr>
					<td><label>Email</label></td>
					<td><input id="txtEmail" maxlength="50" size="50"></td>
				</tr>

				<tr>
					<td><label>Usuário</label></td>
					<td><input id="txtUsuario" maxlength="20" size="20"></td>
				</tr>

				<tr>
					<td><label>Senha</label></td>
					<td><input id="txtSenha" type="password" maxlength="8"
						size="8"></td>
				</tr>
				<tr>
					<td><label>Confirma Senha</label></td>
					<td><input id="txtCsenha" type="password" maxlength="8"
						size="8"></td>
				</tr>
			</table>
		</fieldset>
		<br>
		<center>
			<a href=" CadastroAluno.jsp"> <img
				style="bottom: 20px; border-color: #D6EBFF" alt="Anterior"
				src="C:\Documents and Settings\maiksv\Meus
documentos\USJT\TCC\previus.png"></a>
			<a href="links.jsp"><img
				style="bottom: 20px; border-color: #D6EBFF" alt="Proxima"
				src="C:\Documents and Settings\maiksv\Meus
documentos\USJT\TCC\next.png"></a>
			<br> <br>
			<button id="cmdIncluir" type="submit" style="width: 70px;">
				Incluir</button>
			<button id="cmdCancelar" type="button" style="width: 70px;">
				Cancelar</button>
		</center>
		</form>