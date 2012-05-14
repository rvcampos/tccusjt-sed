<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<div class="navbar navbar-fixed-top">
<div class="navbar-inner">
	<div class="container">
		<a class="brand" href="${app_context}">Sistema de Ensino à distancia</a>
		<ul class="nav">
		<shiro:authenticated>
		<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Cursos<b class="caret"></b></a>
			<ul class="dropdown-menu">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td valign="top">
							<li class="nav-header">Cursos</li>
							<shiro:hasRole name="aluno">
							<li><a href="${app_context}aluno/cursos">Cursos Disponíveis</a></li>
							<li><a href="${app_context}aluno/meusCursos">Meus Cursos</a></li>
							</shiro:hasRole>
							<shiro:hasRole name="professor">
							<li><a href="${app_context}professor/meusCursos">Listar Meus Cursos</a></li>
							</shiro:hasRole>
						</td>
					</tr>
				</table>
			</ul>
		</li>
		<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Conta<b class="caret"></b></a>
			<ul class="dropdown-menu">
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td valign="top">
						<li class="nav-header">Minha Conta</li>
						<shiro:hasRole name="aluno">
							<li><a href="${app_context}aluno/alterarDados">Alterar Dados Cadastrais</a></li>
						</shiro:hasRole>
						
						<shiro:hasRole name="professor">
							<li><a href="${app_context}professor/alterarDados">Alterar Dados Cadastrais</a></li>
						</shiro:hasRole>
						</td>
					</tr>
				</table>
			</ul>
		</li>
		</shiro:authenticated>
		<shiro:notAuthenticated>
		<li class="divider-vertical"></li>
		<li><a href="${app_context}login">Login</a></li>
		<li class="divider-vertical"></li>
		</shiro:notAuthenticated>
		<shiro:authenticated>
		<li class="divider-vertical"></li>
		<li><a href="${app_context}logout">Logout</a></li>
		<li class="divider-vertical"></li>
		</shiro:authenticated>
		</ul>
	</div>
</div>
</div>
