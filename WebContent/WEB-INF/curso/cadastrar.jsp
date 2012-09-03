<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Disciplina</title>
</head>
<script>


</script>
		<form action="create" method="post" id="formDisciplina">
			<legend>
				<b>Cadastro </b>
			</legend>
		<table>
		<tr>
		<td>
			<div class="tabbable">
			<ul class="nav nav-tabs"> 
			<li class="active"><a href="#1" data-toggle="tab">Dados da Disciplina</a></li>  
			</ul>
			<div class="tab-content">  
			<div class="tab-pane active" id="1">  
			<table border="0" class="table">
			
	<tr>
		<td><label>Nome</label></td>
		<td><input type="text" name="txtNomeDisciplina" id="txtNomeDisciplina" name="txtNome" maxlength="50" size="50"></td>
	</tr>
    
    
    <tr>
        <td align="right"><label> Data Inicio </label></td>
		<td><input name="txtDataInicio" id="txtDataInicio" type='text' maxlength="10" size="12" alt="39/19/2999"></td>
    </tr>  
         
    <tr>
        <td align="right"><label> Data Termino </label></td>
		<td><input name="txtDataTermino" id="txtDataTermino" type='text' maxlength="10" size="12" alt="39/19/2999"></td>
    </tr>  
    
     <tr>
        <td align="right"><label>Descrição</label></td>
		<td><textarea rows="10" cols="25" name="txtDesc">${txtDesc}</textarea></td>
    </tr>  
   
</table>

<br>
<center>
<button id="cmdIncluir" type="submit" style="width: 70px;"> Incluir</button>
<button id="cmdCancelar" type="button" style="width: 70px;"> Cancelar</button>
</center>				
	
		</form>
