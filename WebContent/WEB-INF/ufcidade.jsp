<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<select name="city_id" id="combobox_city">
	<option></option>
	<c:forEach var="cities" items="${list_city}">
		<option value="${cities.id_cidade}" id="${cities.id_cidade}">${cities.nome}</option>
	</c:forEach>
</select>
