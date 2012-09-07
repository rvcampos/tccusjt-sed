<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<select name="cidade" id="combobox_city">
	<option></option>
	<c:forEach var="cities" items="${list_city}">
		<option value="${cities.id_cidade}" id="${cities.id_cidade}">${cities.nome}</option>
	</c:forEach>
</select>
