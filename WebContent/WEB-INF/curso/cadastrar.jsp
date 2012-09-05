<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Disciplina</title>

<script type="text/javascript">

function gerar(id, qtdquest, qtdalt)
{
	if(qtdquest == '' || qtdalt == '')
	{
		alert('Preencha a quantiade de Quest�es e alternativas');
		return false;
	}
	var i,j;
	var html = "";
	var defcontrol = '<div class="control-group"><label class="control-label" for="{txtname}">{label}</label><div class="controls"><input name="{txtname}" id="{txtname}" type="text" maxlength="300"/></div></div>';
	var tabs = '<div class="tabbable"><ul class="nav nav-tabs">{tabsli}</ul><div class="tab-content">{tabshtml}</div></div>';
	var litab = '<li class="active"><a href="#'+id+'{num}" data-toggle="tab">{qnum}</a></li>';
	
	for(i = 0; i < qtdquest; i++)
	{
		html = html + defcontrol.format({ txtname: 'txt' + id + 'quest' + i, label: 'Quest�o: ' + i});
		for(j = 0; j < qtdalt; j++)
		{
			html = html + defcontrol.format({ txtname: 'txt' + id + 'quest' + i +'alt' + j, label: 'Alternativa ' + j});
		}
	}
	$('#'+id).html(html);
}

</script>
</head>
<form action="create" class="form-horizontal" method="post"
	id="formDisciplina">
	<legend>
		<b>Cadastro </b>
	</legend>
	<div class="tabbable">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#1" data-toggle="tab">Dados da
					Disciplina</a></li>
			<li><a href="#2" data-toggle="tab">B�sico</a></li>
			<li><a href="#3" data-toggle="tab">Intermedi�rio</a></li>
			<li><a href="#4" data-toggle="tab">Avan�ado</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="1">
				<div class="control-group">
					<label class="control-label" for="txtNomeDisciplina">Nome
						da Disciplina</label>
					<div class="controls">
						<input type="text" id="txtNomeDisciplina" name="txtNomeDisciplina"
							value="${txtNome}" placeholder="Nome Completo" maxlength="50" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDataInicio">Data de
						Inicio</label>
					<div class="controls">
						<input name="txtDataInicio" id="txtDataInicio"
							placeholder="Data de In�cio" type='text' maxlength="10" size="12"
							alt="39/19/2999" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDataTermino">Data de
						T�rmino</label>
					<div class="controls">
						<input name="txtDataTermino" id="txtDataTermino" type='text'
							maxlength="10" size="12" alt="39/19/2999" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDesc">Descri��o</label>
					<div class="controls">
						<textarea rows="10" cols="25" name="txtDesc">${txtDesc}</textarea>
					</div>
				</div>
			</div>
			<div class="tab-pane active" id="2">
				<div class="control-group">
					<label class="control-label" for="txtQtd1">Quantidade de
						Quest�es</label>
					<div class="controls">
						<input type="text" name="qtdquestoesBas" id="qtdquestbasico"
							alt="99" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtQtdAlt">Quantidade de
						Alternativas</label>
					<div class="controls">
						<input type="text" name="qtdaltBas" id="qtdaltbasico" alt="99" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls"><input type="button" onclick="gerar('questoesBasico', $('#qtdquestbasico').val(), $('#qtdaltbasico').val());" value="gerar"/></div>
				</div>
				<div id="questoesBasico"></div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span2">
			<input class="btn btn-primary" value="Salvar" type="submit"
				name="btnSalvar" />
		</div>

		<div class="span2">
			<a href="${app_context}professor/meusCursos" class="btn">Cancelar</a>
		</div>

	</div>
</form>