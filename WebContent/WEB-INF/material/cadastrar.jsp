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
			
		<table>
		<tr>
		<td>
			<div class="tabbable">
			<ul class="nav nav-tabs"> 
			<li class="active"><a href="#1" data-toggle="tab">Cadastrar Material</a></li>  
			
			</ul>
			<div class="tab-content">  
			<div class="tab-pane active" id="1">  
			<table border="0" class="table">
    
    
    <tr>
        <td align="right"> 
		<label> Módulo </label>
		</td>
		<td>
		<select  style="width: 150px;" id="CboModulo" >
		<option value="basico" >Básico </option>
		<option value="inter" >Intermediário </option>
		<option value="avancao" >Avançado </option>
		
		</select>
		
        </td>
    </tr>  
    
    <tr>
		<td><label>Arquivo</label></td>
		<td><input type="file" id="Arquivo" name="Arquivo"></td>
	</tr>
         
   
</table>

<br>
<center>
<button id="cmdIncluir" type="submit" style="width: 70px;"> Incluir</button>
<button id="cmdCancelar" type="submit" style="width: 70px;"> Cancelar</button>
</center>				
	
		</form>
