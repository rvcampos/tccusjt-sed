<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<head>
<script type="text/javascript">
	function changeLayout(layout)
	{
		return changeLayout(layout, null);
	}
	function changeLayout(layout, callback) {
		$.ajax({
			url : "${app_context}layout",
			type : "POST",
			cache : false,
			data : {
				"layout" : layout
			},
			dataType : "text",
			success : function(msg) {
				if (callback) {
					callback();
				}
				$('#bootcss').attr('href','${app_context}css/bootstrap'+layout+'.css');
			}
		});
	}
</script>
</head>

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="${app_context}">S.E.D</a>
			<ul class="nav">
				<shiro:authenticated>
					<shiro:hasRole name="admin">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><i class="icon-user"></i>Administrador<b
								class="caret"></b></a>
							<ul class="dropdown-menu">
								<table cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td valign="top">
											<li><a href="${app_context}admin/cadastrar">Cadastrar
													Administrador</a></li>
											<li><a href="${app_context}admin/listar">Listar
													Administrador</a></li>
										</td>
									</tr>
								</table>
							</ul></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><i class="icon-user"></i>Aluno<b
								class="caret"></b></a>
							<ul class="dropdown-menu">
								<table cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td valign="top">
											<li><a href="${app_context}aluno/cadastrar">Cadastrar
													Aluno</a></li>
											<li><a href="${app_context}aluno/listar">Listar
													Aluno</a></li>
											<li><a href="${app_context}admin/listarBloqueios">Listar
													Bloqueios</a></li>
										</td>
									</tr>
								</table>
							</ul></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><i class="icon-user"></i>Professor<b
								class="caret"></b></a>
							<ul class="dropdown-menu">
								<table cellpadding="0" cellspacing="0" border="0">
									<tr>
										<td valign="top">
											<li><a href="${app_context}professor/cadastrar">Cadastrar
													Professor</a></li>
											<li><a href="${app_context}professor/listar">Listar
													Professor</a></li>
										</td>
									</tr>
								</table>
							</ul></li>
					</shiro:hasRole>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="icon-book"></i>Cursos<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td valign="top">
										<li class="nav-header">Cursos</li> <shiro:hasRole name="aluno">
											<li><a href="${app_context}curso/listar">Cursos
													Disponíveis</a></li>
											<li><a href="${app_context}aluno/meusCursos">Meus
													Cursos</a></li>
										</shiro:hasRole> <shiro:hasRole name="professor">
											<li><a href="${app_context}professor/meusCursos">Listar
													Meus Cursos</a></li>
											<li><a href="${app_context}curso/cadastrar">Cadastrar
													Curso</a></li>
										</shiro:hasRole> <shiro:hasRole name="admin">
											<li><a href="${app_context}curso/listar">Cursos
													Disponíveis</a></li>
											<li><a href="${app_context}curso/cadastrar">Cadastrar
													Curso</a></li>
										</shiro:hasRole>
									</td>
								</tr>
							</table>
						</ul></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="icon-user"></i>Conta<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td valign="top">
										<li class="nav-header">Minha Conta</li> <shiro:hasRole
											name="aluno">
											<li><a href="${app_context}aluno/alterarDados">Alterar
													Dados Cadastrais</a></li>
										</shiro:hasRole> <shiro:hasRole name="professor">
											<li><a href="${app_context}professor/alterarDados">Alterar
													Dados Cadastrais</a></li>
										</shiro:hasRole> <shiro:hasRole name="admin">
											<li><a href="${app_context}admin/alterarDados">Alterar
													Dados Cadastrais</a></li>
										</shiro:hasRole>
									</td>
								</tr>
							</table>
						</ul></li>
				</shiro:authenticated>
				<shiro:notAuthenticated>
					<li class="divider-vertical" />
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" href="#">Acesso <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td valign="top">
										<li class="nav-header">Aluno</li>
										<li><a href="${app_context}login?src=aluno">Login
												Aluno</a></li>
										<li><a href="${app_context}aluno/cadastrar">Cadastrar</a></li>
										<li><a href="${app_context}aluno/esqueciminhasenha">Esqueci
												Minha senha</a></li>
										<li><a href="${app_context}aluno/reenviaAtivacao">Re-enviar
												ativação</a></li>
									</td>
									<td class="td-divider" />
									<td valign="top">
										<li class="nav-header">Professor</li>
										<li><a href="${app_context}login?src=professor">Login
												Professor</a></li>
										<li><a href="${app_context}professor/esqueciminhasenha">Esqueci
												Minha senha</a></li>
									</td>
									<td class="td-divider" />
									<td valign="top">
										<li class="nav-header">Administrador</li>
										<li><a href="${app_context}login?src=admin">Login
												Administrador</a></li>
										<li><a href="${app_context}admin/esqueciminhasenha">Esqueci
												Minha senha</a></li>
									</td>
								</tr>
							</table>
						</ul></li>
					<li class="divider-vertical"></li>
				</shiro:notAuthenticated>
				<shiro:authenticated>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" href="#">Layout <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<table cellpadding="0" cellspacing="0" border="0">
								<tr>
									<td valign="top">
										<li class="nav-header">Aluno</li>
										<li><a href="javascript: changeLayout(''); ">Padrão</a></li>
										<li><a href="javascript: changeLayout(1); ">Layout 1</a></li>
										<li><a href="javascript: changeLayout(2); ">Layout 2</a></li>
										<li><a href="javascript: changeLayout(3); ">Layout 3</a></li>
										<li><a href="javascript: changeLayout(4); ">Layout 4</a></li>
										<li>Para visualizar o novo layout, re-carregue a página</li>
									</td>
									<td class="td-divider" />
							</table>
						</ul></li>
					<li class="divider-vertical"></li>
					<li><a href="${app_context}logout"><i class="icon-off"></i>Logout</a></li>
					<li class="divider-vertical"></li>
				</shiro:authenticated>
			</ul>
		</div>
	</div>
</div>
