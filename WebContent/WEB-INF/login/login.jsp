<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>SED</title>
</head>
<body onLoad="document.form_login.username.focus()">

			<shiro:notAuthenticated>
				<form method="post" action="login" name="form_login">
					<table>
						<tr>
							<td>Usuário</td>
							<td><input type="text" name="username" /></td>
						</tr>
						<tr>
							<td>Senha</td>
							<td><input type="password" name="password" /></td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="Login" class="btn" id="btnLogin" data-loading-text="Logando..." onclick="$('#btnLogin').button('loading');" />
							</td>
						</tr>
					</table>
				</form>
			</shiro:notAuthenticated>
			<br/><br/>
</body>
</html>