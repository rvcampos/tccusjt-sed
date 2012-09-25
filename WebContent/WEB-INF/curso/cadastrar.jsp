<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cadastro de Disciplina</title>

<script type="text/javascript" charset="UTF-8">
	function gerar(id, qtdquest, qtdalt)
{
	if(qtdquest == '' || qtdalt == '')
	{
		alert('Preencha a quantiade de Questões e alternativas');
		return false;
	}
	var i,j;
	var html = "";
	var defcontrol = '<div class="control-group"><label class="control-label" for="{txtname}">{label}</label><div class="controls"><input name="{txtname}" class="input-xxlarge" id="{txtname}" type="text" maxlength="300" value="{valor}"/></div></div>';
	var alter = '<div class="control-group"><label class="control-label" for="{txtname}"><span class="badge badge-info">{label}</span></label><div class="controls"> <input name="{txtname}" id="{txtname}" type="text" maxlength="300" value="{valor}"/>&nbsp&nbsp&nbsp<input type="radio" name="{radioname}" value="{chkval}" /></div></div>';
	
	for(i = 1; i <= qtdquest; i++)
	{
		var valq = $('#txt'+ id + 'quest' + i).val();
		if(valq == null)
		{
			valq = '';
		}
		html = html + defcontrol.format({ txtname: 'txt' + id + 'quest' + i, label: 'Questão: ' + i, valor: valq});
		for(j = 1; j <= qtdalt; j++)
		{
			var vala = $('#txt'+ id + 'quest' + i +'alt' + j).val();
			if(vala == null)
			{
				vala = '';
			}
			html = html + alter.format({ txtname: 'txt' + id + 'quest' + i +'alt' + j, label: 'Alternativa ' + j, radioname: 'opt' + id + '' + i, chkval: '' + j, valor: vala});
		}
	}
	$('#'+id).html(html);
}

function gerarMaterial(id, qtd, label)
	{
		var i,j;
		var html = "";
		var defcontrol = '<div class="control-group"><label class="control-label" for="{txtname}">{lbl}</label><div class="controls"><input type="file" name="{txtname}" value="upload" /></div></div>';
		for(i = 1; i <= qtd; i++)
		{
			html = html + defcontrol.format({ txtname: ''+label, lbl: 'Material: ' + i});
		}
		$('#'+id).html(html);
	}
	</script>
</head>
<form action="${metodo}" class="form-horizontal" method="post"
	id="formDisciplina" enctype="multipart/form-data" accept-charset="UTF-8">
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
							value="${txtNomeDisciplina}" placeholder="Nome Completo" maxlength="50" />
							<input type="hidden" id="id_disciplina" name="id_disciplina"
							value="${id_disciplina}" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDataInicio">Data de
						Inicio</label>
					<div class="controls">
						<div class="input-append date datepicker" data-date="${txtDataInicio}"
							data-date-format="dd/mm/yyyy" style="float: left">
							<input class="span2" size="16" type="text" name="txtDataInicio"
								value="${txtDataInicio}" alt="39/19/9999"> <span class="add-on"><i
								class="icon-calendar"></i> </span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="txtDataTermino">Data de
						Término</label>
					<div class="controls">
						<div class="input-append date datepicker" data-date="${txtDataTermino}"
							data-date-format="dd/mm/yyyy" style="float: left">
							<input class="span2" size="16" type="text" name="txtDataTermino"
								value="${txtDataTermino}" alt="39/19/9999"> <span class="add-on"><i
								class="icon-calendar"></i> </span>
						</div>
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
								<label class="control-label" for="qtdMatBas">Quantidade
									Material</label>
								<div class="controls">
									<input type="text" name="qtdMatBas" id="qtdMatBas" value="${qtdMatBas}" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerarMaterial('materialBasico', $('#qtdMatBas').val(), 'matBasico');"
										value="gerar" />
								</div>
							</div>
							<div id="materialBasico"> </div>
						</div>
						<div class="tab-pane" id="basicqst">
							<div class="control-group">
								<label class="control-label" for="txtQtd1">Quantidade de
									Questões</label>
								<div class="controls">
									<input type="text" name="qtdquestoesBas" id="qtdquestbasico"
										alt="99" maxlength="2"  value="${qtdquestoesBas}"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="txtQtdAlt">Quantidade
									de Alternativas</label>
								<div class="controls">
									<input type="text" name="qtdaltBas" id="qtdaltbasico" alt="99" maxlength="2" value="${qtdaltBas}"/>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerar('questoesBasico', $('#qtdquestbasico').val(), $('#qtdaltbasico').val());"
										value="gerar" />
								</div>
							</div>
							<div id="questoesBasico">
							<c:set var="countQuest" value="${1}"/>
								<c:forEach var="quest" items="${basico.avaliacao.questoes}">
									<div class="control-group">
										<label class="control-label"
											for="txtquestoesBasicoquest${countQuest}">Questão
											${countQuest}</label>
										<div class="controls">
											<input type="text" name="txtquestoesBasicoquest${countQuest}"
												id="txtquestoesBasicoquest${countQuest}" class="input-xxlarge" maxlength="300" value="${quest.conteudo}" />
											<input type="hidden" name="id_basico" value="${basico.id_modulo }" />
										</div>
									</div>
									<c:set var="countAlt" value="${1}"/>
									<c:forEach var="alt" items="${quest.alternativas}">
										<div class="control-group">
											<label class="control-label"
												for="txtquestBasicoquest${countQuest}alt${countAlt}"> <span
												class="badge badge-info">Alternativa </span>
											</label>
											<div class="controls">
												<input name="txtquestoesBasicoquest${countQuest}alt${countAlt}" id="txtquestoesBasicoquest${countQuest}alt${countAlt}" type="text"
													maxlength="300" value="${alt.conteudo}" /> &nbsp&nbsp&nbsp<input
													type="radio" name="optquestoesBasico${countQuest}" value="${countQuest}" <c:if test="${alt.correta}">checked="checked"</c:if> />
											</div>
										</div>
										<c:set var="countAlt" value="${countAlt + 1}"/>
									</c:forEach>
									<c:set var="countQuest" value="${countQuest + 1}"/>
								</c:forEach>
							</div>
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
								<label class="control-label" for="qtdMatInt">Quantidade
									Material</label>
								<div class="controls">
									<input type="text" name="qtdMatInt" id="qtdMatInt" value="${qtdMatInt}" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerarMaterial('materialIntermediario', $('#qtdMatInt').val(), 'matIntermediario');"
										value="gerar" />
								</div>
							</div>
							<div id="materialIntermediario"> </div>
						</div>
						<div class="tab-pane" id="intqst">
							<div class="control-group">
								<label class="control-label" for="qtdquestoesInt">Quantidade de
									Questões</label>
								<div class="controls">
									<input type="text" name="qtdquestoesInt" id="qtdquestintermediario"
										alt="99" value="${qtdquestoesInt}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="qtdaltInt">Quantidade
									de Alternativas</label>
								<div class="controls">
									<input type="text" name="qtdaltInt" id="qtdaltintermediario" alt="99" value="${qtdaltInt}"/>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerar('questoesIntermediario', $('#qtdquestintermediario').val(), $('#qtdaltintermediario').val());"
										value="gerar" />
								</div>
							</div>
							<div id="questoesIntermediario">
							<c:set var="countQuest" value="${1}"/>
								<c:forEach var="quest" items="${intermediario.avaliacao.questoes}">
									<div class="control-group">
										<label class="control-label"
											for="txtquestoesIntermediarioquest${countQuest}">Questão
											${countQuest}</label>
										<div class="controls">
											<input type="text" name="txtquestoesIntermediarioquest${countQuest}"
												id="txtquestoesIntermediarioquest${countQuest}" class="input-xxlarge" maxlength="300" value="${quest.conteudo}" />
											<input type="hidden" name="id_intermediario" value="${intermediario.id_modulo }" />
										</div>
									</div>
									<c:set var="countAlt" value="${1}"/>
									<c:forEach var="alt" items="${quest.alternativas}">
										<div class="control-group">
											<label class="control-label"
												for="txtquestIntermediarioquest${countQuest}alt${countAlt}"> <span
												class="badge badge-info">Alternativa </span>
											</label>
											<div class="controls">
												<input name="txtquestoesIntermediarioquest${countQuest}alt${countAlt}" id="txtquestoesIntermediarioquest${countQuest}alt${countAlt}" type="text"
													maxlength="300" value="${alt.conteudo}" /> &nbsp&nbsp&nbsp<input
													type="radio" name="optquestoesIntermediario${countQuest}" value="${countQuest}" <c:if test="${alt.correta}">checked="checked"</c:if> />
											</div>
										</div>
										<c:set var="countAlt" value="${countAlt + 1}"/>
									</c:forEach>
									<c:set var="countQuest" value="${countQuest + 1}"/>
								</c:forEach>
							</div>
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
								<label class="control-label" for="qtdMatAdv">Quantidade
									Material</label>
								<div class="controls">
									<input type="text" name="qtdMatAdv" id="qtdMatAdv" value="${qtdMatAdv}" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerarMaterial('materialAvancado', $('#qtdMatAdv').val(), 'matAvancado');"
										value="gerar" />
								</div>
							</div>
							<div id="materialAvancado"> </div>
						</div>
						<div class="tab-pane" id="advqst">
							<div class="control-group">
								<label class="control-label" for="qtdquestoesAdv">Quantidade de
									Questões</label>
								<div class="controls">
									<input type="text" name="qtdquestoesAdv" id="qtdquestavancado"
										alt="99" value="${qtdquestoesAdv}"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="qtdaltAdv">Quantidade
									de Alternativas</label>
								<div class="controls">
									<input type="text" name="qtdaltAdv" id="qtdaltavancado" alt="99" value="${qtdaltAdv}"/>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="button"
										onclick="gerar('questoesAvancado', $('#qtdquestavancado').val(), $('#qtdaltavancado').val());"
										value="gerar" />
								</div>
							</div>
							<div id="questoesAvancado">
								<c:set var="countQuest" value="${1}"/>
								<c:forEach var="quest" items="${avancado.avaliacao.questoes}">
									<div class="control-group">
										<label class="control-label"
											for="txtquestoesAvancadoquest${countQuest}">Questão
											${countQuest}</label>
										<div class="controls">
											<input type="text" name="txtquestoesAvancadoquest${countQuest}"
												id="txtquestoesAvancadoquest${countQuest}" class="input-xxlarge" maxlength="300" value="${quest.conteudo}" />
											<input type="hidden" name="id_avancado" value="${avancado.id_modulo }" />
										</div>
									</div>
									<c:set var="countAlt" value="${1}"/>
									<c:forEach var="alt" items="${quest.alternativas}">
										<div class="control-group">
											<label class="control-label"
												for="txtquestBasicoquest${countQuest}alt${countAlt}"> <span
												class="badge badge-info">Alternativa </span>
											</label>
											<div class="controls">
												<input name="txtquestoesAvancadoquest${countQuest}alt${countAlt}" id="txtquestoesAvancadoquest${countQuest}alt${countAlt}" type="text"
													maxlength="300" value="${alt.conteudo}" /> &nbsp&nbsp&nbsp<input
													type="radio" name="optquestoesAvancado${countQuest}" value="${countQuest}" <c:if test="${alt.correta}">checked="checked"</c:if> />
											</div>
										</div>
										<c:set var="countAlt" value="${countAlt + 1}"/>
									</c:forEach>
									<c:set var="countQuest" value="${countQuest + 1}"/>
								</c:forEach>
							</div>
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