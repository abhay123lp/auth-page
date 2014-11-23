<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Object error = request.getAttribute("error");
	if (error == null) {
		request.setAttribute("error", "display: none;");
	}
	Object username = request.getAttribute("username");
	if (username == null) {
		request.setAttribute("username", "");
	}
	Object password = request.getAttribute("password");
	if (password == null) {
		request.setAttribute("password", "");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="US-ASCII">
<title>Login</title>
</head>
<body>
	<fieldset>
		<form action="authenticate" method="post">
			Username:*<input type="text" name="username" value="<%=request.getAttribute("username")%>"> <br />
			Password:*<input type="password" name="password" value="<%=request.getAttribute("password")%>"> <br /> 
			<span style="<%=request.getAttribute("error")%>"> 
				Either username or password is wrong</span> <br /> 
			<input type="submit" value="Login">
		</form>
	</fieldset>
</body>
</html>