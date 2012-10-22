<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${nomeCurso} - ${nivel}</title>
<script type="text/javascript">
	function gerarMaterial(id, qtd, label) {
		var i, j;
		var html = "";
		var defcontrol = '<div class="control-group"><label class="control-label" for="{txtname}">{lbl}</label><div class="controls"><input type="file" name="{txtname}" value="upload" /></div></div>';
		for (i = 1; i <= qtd; i++) {
			html = html + defcontrol.format({
				txtname : '' + label,
				lbl : 'Material: ' + i
			});
		}
		$('#' + id).html(html);
	}
</script>
</head>

<form id="download" action="${app_context}material/download"
	method="post">
	<input type="hidden" name="material" id="frmMaterial" value="" />
</form>

<form id="remover" action="${app_context}material/delete" method="post">
	<input type="hidden" name="material" id="deleteMat" value="" /> <input
		type="hidden" name="id_disciplina" value="${disciplina.id_disciplina}" />
</form>

<form class="form-horizontal" action="${app_context}curso/upload"
	enctype="multipart/form-data" accept-charset="UTF-8" method="post">
	<div class="tabbable">
		<ul class="nav nav-tabs">
			<li class="active"><a href="#1" data-toggle="tab">Basico</a></li>
			<li><a href="#2" data-toggle="tab">Intermediário</a></li>
			<li><a href="#3" data-toggle="tab">Avançado</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="1">
				<div class="control-group">
					<label class="control-label" for="qtdMatBas">Quantidade
						Material</label>
					<div class="controls">
						<input type="text" name="qtdMatBas" id="qtdMatBas"
							value="${qtdMatBas}" /> <input type="hidden"
							name="id_disciplina" value="${disciplina.id_disciplina}" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="button" class="btn btn-info"
							onclick="gerarMaterial('materialBasico', $('#qtdMatBas').val(), 'matBasico');"
							value="gerar" />
					</div>
				</div>
				<div id="materialBasico"></div>

				<table class="table">
					<tr>
						<th>Nome</th>
						<th>Download</th>
						<th>Remover</th>
					</tr>
					<c:forEach var="basic" items="${basico}">
						<tr>
							<td>${basic.nome}</td>
							<td><a
								href="javascript:$('#frmMaterial').val('${basic.id_material}');$('#download').submit();"><i
									class="icon-print"></i></a></td>
							<td><a
								href="javascript:$('#deleteMat').val('${basic.id_material}');$('#remover').submit();"><i
									class="icon-remove"></i></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="tab-pane" id="2">
				<div class="control-group">
					<label class="control-label" for="qtdMatInt">Quantidade
						Material</label>
					<div class="controls">
						<input type="text" name="qtdMatInt" id="qtdMatInt"
							value="${qtdMatInt}" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="button" class="btn btn-info"
							onclick="gerarMaterial('materialIntermediario', $('#qtdMatInt').val(), 'matIntermediario');"
							value="gerar" />
					</div>
				</div>
				<div id="materialIntermediario"></div>

				<table class="table">
					<tr>
						<th>Nome</th>
						<th>Download</th>
						<th>Remover</th>
					</tr>
					<c:forEach var="basic" items="${intermediario}">
						<tr>
							<td>${basic.nome}</td>
							<td><a
								href="javascript:$('#frmMaterial').val('${basic.id_material}');$('#download').submit();"><i
									class="icon-print"></i></a></td>
							<td><a
								href="javascript:$('#deleteMat').val('${basic.id_material}');$('#remover').submit();"><i
									class="icon-remove"></i></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="tab-pane" id="3">
				<div class="control-group">
					<label class="control-label" for="qtdMatAdv">Quantidade
						Material</label>
					<div class="controls">
						<input type="text" name="qtdMatAdv" id="qtdMatAdv"
							value="${qtdMatAdv}" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="button" class="btn btn-info"
							onclick="gerarMaterial('materialAvancado', $('#qtdMatAdv').val(), 'matAvancado');"
							value="gerar" />
					</div>
				</div>
				<div id="materialAvancado"></div>

				<table class="table">
					<tr>
						<th>Nome</th>
						<th>Download</th>
						<th>Remover</th>
					</tr>
					<c:forEach var="basic" items="${avancado}">
						<tr>
							<td>${basic.nome}</td>
							<td><a
								href="javascript:$('#frmMaterial').val('${basic.id_material}');$('#download').submit();"><i
									class="icon-print"></i></a></td>
							<td><a
								href="javascript:$('#deleteMat').val('${basic.id_material}');$('#remover').submit();"><i
									class="icon-remove"></i></a></td>
						</tr>
					</c:forEach>
				</table>
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
<span></span>
