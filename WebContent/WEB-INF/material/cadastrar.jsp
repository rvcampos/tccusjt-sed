<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Material</title>
</head>
<script>


</script>
		<form action="create" method="post" id="formMaterial">
			<legend>
				<b>Cadastro de Material </b>
			</legend>
		<table>
		<tr>
		<td>
			<div class="tabbable">
			<ul class="nav nav-tabs"> 
			<li class="active"><a href="#1" data-toggle="tab">Cadastrar</a></li>  
			
			</ul>
			<div class="tab-content">  
			<div class="tab-pane active" id="1">  
			<table border="0" class="table">
    
    
    <tr>
        <td align="right"> 
		<label> M�dulo </label>
		</td>
		<td>
		<select  style="width: 150px;" id="CboModulo" >
		<option value="basico" >B�sico </option>
		<option value="inter" >Intermedi�rio </option>
		<option value="avancao" >Avan�ado </option>
		
		</select>
		
        </td>
    </tr>  
         
    <tr>
        <td align="right">
        <label >Arquivo</label>		
		</td>
		<td>
		<input id="txtDiretorio"  size="40" >	
			
			
		</td>
		<td>
		<button id="cmdDiretorio" style="width: 110px;" type="file">Escolher Arquivo</button>	
		</td>
    </tr>
   
</table>

<br>
<center>
<button id="cmdIncluir" type="submit" style="width: 70px;"> Incluir</button>
<button id="cmdCancelar" type="submit" style="width: 70px;"> Cancelar</button>
</center>				
	
		</form>
