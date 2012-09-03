<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/rssutils.tld" prefix="rss"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<meta charset="UTF-8" />
<jsp:include page="header.jsp" />

</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="container-fluid" style="padding-top: 60px;">
	<div class="row-fluid">
			<div class="span10">

				<a href="http://192.168.1.123:443/?0,0,1,13,0&nn=escobar" target="_blank">CHAT</a>

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