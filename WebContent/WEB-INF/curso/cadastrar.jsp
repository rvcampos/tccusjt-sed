<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Disciplina</title>

<script type="text/javascript">
	function gerar(id, qtdquest, qtdalt)
{
	if(qtdquest == '' || qtdalt == '')
	{
		alert('Preencha a quantiade de Questões e alternativas');
		return false;
	}
	var i,j;
	var html = "";
	var defcontrol = '<div class="control-group"><label class="control-label" for="{txtname}">{label}</label><div class="controls"><input name="{txtname}" id="{txtname}" type="text" maxlength="300"/></div></div>';
	var alter = '<div class="control-group"><label class="control-label" for="{txtname}"><span class="badge badge-info">{label}</span></label><div class="controls"> <input name="{txtname}" id="{txtname}" type="text" maxlength="300"/>&nbsp&nbsp&nbsp<input type="radio" name="{radioname}" value="{chkval}" /></div></div>';
	
	for(i = 1; i <= qtdquest; i++)
	{
		html = html + defcontrol.format({ txtname: 'txt' + id + 'quest' + i, label: 'Questão: ' + i});
		for(j = 1; j <= qtdalt; j++)
		{
			html = html + alter.format({ txtname: 'txt' + id + 'quest' + i +'alt' + j, label: 'Alternativa ' + j, radioname: 'opt' + id + '' + i, chkval: '' + j});
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
			<li><a href="#2" data-toggle="tab">Basico</a></li>
			<li><a href="#3" data-toggle="tab">Intermediário</a></li>
			<li><a href="#4" data-toggle="tab">Avançado</a></li>
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
							placeholder="Data de Início" type='text' maxlength="10" size="12"
							alt="39/19/2999" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDataTermino">Data de
						Término</label>
					<div class="controls">
						<input name="txtDataTermino" id="txtDataTermino" type='text'
							maxlength="10" size="12" alt="39/19/2999" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDesc">Descrição</label>
					<div class="controls">
						<textarea rows="10" cols="25" name="txtDesc">${txtDesc}</textarea>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="2">
				<div class="tabbable">
					<ul class="nav nav-pills">
						<li><a href="#basicmat" data-toggle="tab">Material</a></li>
						<li><a href="#basicqst" data-toggle="tab">Questões</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane" id="basicmat">
							<div class="control-group">
								<label class="control-label" for="txtQtd1">Material</label>
								<div class="controls">
									<input type="file" name="txtmaterial" value="upload" />
								</div>
							</div>
						</div>
						<div class="tab-pane" id="basicqst">
							<div class="control-group">
								<label class="control-label" for="txtQtd1">Quantidade de
									Questões</label>
								<div class="controls">
									<input type="text" name="qtdquestoesBas" id="qtdquestbasico"
										alt="99" maxlength="2"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="txtQtdAlt">Quantidade
									de Alternativas</label>
								<div class="controls">
									<input type="text" name="qtdaltBas" id="qtdaltbasico" alt="99" maxlength="2"/>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerar('questoesBasico', $('#qtdquestbasico').val(), $('#qtdaltbasico').val());"
										value="gerar" />
								</div>
							</div>
							<div id="questoesBasico"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="3">
				<div class="tabbable">
					<ul class="nav nav-pills">
						<li><a href="#intmat" data-toggle="tab">Material</a></li>
						<li><a href="#intqst" data-toggle="tab">Questões</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane" id="intmat">
							<div class="control-group">
								<label class="control-label" for="txtQtd2">Material</label>
								<div class="controls">
									<input type="file" name="txtmaterial" value="upload" />
								</div>
							</div>
						</div>
						<div class="tab-pane" id="intqst">
							<div class="control-group">
								<label class="control-label" for="qtdquestoesInt">Quantidade de
									Questões</label>
								<div class="controls">
									<input type="text" name="qtdquestoesInt" id="qtdquestintermediario"
										alt="99" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="qtdaltInt">Quantidade
									de Alternativas</label>
								<div class="controls">
									<input type="text" name="qtdaltInt" id="qtdaltintermediario" alt="99" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerar('questoesIntermediario', $('#qtdquestintermediario').val(), $('#qtdaltintermediario').val());"
										value="gerar" />
								</div>
							</div>
							<div id="questoesIntermediario"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" id="4">
				<div class="tabbable">
					<ul class="nav nav-pills">
						<li><a href="#advmat" data-toggle="tab">Material</a></li>
						<li><a href="#advqst" data-toggle="tab">Questões</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane" id="advmat">
							<div class="control-group">
								<label class="control-label" for="txtQtd1">Material</label>
								<div class="controls">
									<input type="file" name="txtmaterial" value="upload" />
								</div>
							</div>
						</div>
						<div class="tab-pane" id="advqst">
							<div class="control-group">
								<label class="control-label" for="qtdquestoesAdv">Quantidade de
									Questões</label>
								<div class="controls">
									<input type="text" name="qtdquestoesAdv" id="qtdquestavancado"
										alt="99" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="qtdaltAdv">Quantidade
									de Alternativas</label>
								<div class="controls">
									<input type="text" name="qtdaltAdv" id="qtdaltavancado" alt="99" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerar('questoesAvancado', $('#qtdquestavancado').val(), $('#qtdaltavancado').val());"
										value="gerar" />
								</div>
							</div>
							<div id="questoesAvancado"></div>
						</div>
					</div>
				</div>
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