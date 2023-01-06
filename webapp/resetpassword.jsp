<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>
</head>
<body>
<h2 align="center">New Password</h2>
<br>
		<%
			if (request.getAttribute("msg") != null) {
				out.print(request.getAttribute("msg"));
			}
		%>
<table align="center">
		<tr>
			<td>Password</td>
			<td><input type="password" name="new_password"></td>
		</tr>
		<tr>
			<td>Confirm Password</td>
			<td><input type="password" name="cnew_password"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			   <input type="submit" name="action" value="ConfirmPassword" class="btn btn-primary">
			</td>
		</tr>
		
</table>
</body>
</html>