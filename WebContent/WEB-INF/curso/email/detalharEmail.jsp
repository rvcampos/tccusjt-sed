<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://
www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${nomeCurso}</title>
</head>

<div class="tabbable tabs-left">
	<ul class="nav nav-tabs">
		<c:forEach var="cnt" begin="1" end="${numpages}">
			<li class=""><a href="#${cnt}" data-toggle="tab">${cnt}</a></li>
		</c:forEach>
	</ul>
	<div class="tab-content">
		<c:set var="cnts" value="0" />
		<c:set var="ini" value="1" />
		<c:forEach var="cnt" begin="1" end="${numpages}">
			<div class="tab-pane" id="${cnt}">
				<span class="badge badge-info">
					${lista_emails.get(cnts).conteudo} </span>
				<c:set var="cnts" value="${cnts + 1}" />
				<c:if test="${cnts < size}">
					<p />
					<p />
					<span class="badge badge-success">
						${lista_emails.get(cnts).conteudo} </span>
					<c:set var="cnts" value="${cnts + 1}" />
				</c:if>
			</div>
		</c:forEach>
	</div>
</div>
