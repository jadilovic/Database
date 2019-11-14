<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body>
<h3>Welcome to Login Page</h3>

<%= request.getAttribute("message") %>

<form action="/Database/PageServlet" method="post" >
<input type="hidden" name="action" value="dologin" />
<p>Enter your email: </p>
<input type="text" name="email" value="enter email" />
<p>Enter password</p>
<input type="text" name="password" value="enter password" />
<input type="submit" value="OK" /><br/>
</form>
</body>
</html>