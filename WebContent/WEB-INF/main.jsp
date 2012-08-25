<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<div class="container" style="padding-top: 60px;">
<a href="/jchatbox/applet/jchatboxclient.html">CHAT</a>
<jsp:include page="${pagina}"></jsp:include>
</div>

</body>
</html>