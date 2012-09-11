<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="/WEB-INF/rssutils.tld" prefix="rss"%>
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta charset="UTF-8" />
<jsp:include page="../header.jsp" />

</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="container-fluid" style="padding-top: 60px;">
		<div class="row-fluid">
			<div class="span2">
				<div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">Material</li>
              <li class="active"><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li><a href="#">Link</a></li>
              <li class="nav-header">Avaliação</li>
              <c:if test="${fazProva}">
              <li><form action="${app_context}curso/realizaAvaliacao" id="frmAval" method="POST"><input type="hidden" name="matricula" value="${id_matricula}" /><a href="#" onclick="$('#frmAval').submit();">Fazer Prova</form></li>
              </c:if>
              <c:if test="${not fazProva}">
              <f:formatDate value="${dt_aval}" var="dt" pattern="dd/MM/yyyy"/>
              <li>Disponível em - ${dt}</li>
              </c:if>
              <li class="nav-header">Contato</li>
              <li><a href="${urlChat}" target="_blank">Chat</a></li>
              <li><a href="#">E-mail</a></li>
            </ul>
          </div>
			</div>
			<div class="span10">
				<a href="${urlChat}"
					target="_blank">CHAT</a>

				<c:if test="${not empty msgok }">
					<div class="alert alert-success">
						<strong>${msgok}</strong>
					</div>
				</c:if>
				<c:if test="${not empty msgerro }">
					<div class="alert alert-error">
						<strong>${msgerro}</strong>
					</div>
				</c:if>
				<jsp:include page="${pagina}"></jsp:include>
			</div>
		</div>
	</div>

</body>
</html>