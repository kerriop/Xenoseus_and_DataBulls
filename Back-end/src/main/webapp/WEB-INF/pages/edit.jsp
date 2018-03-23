<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style>
			<%@include file="/WEB-INF/pages/style.css" %>
		</style>
		<title>Управление</title>
	</head>
	<body>
		<div align="center" id="content">
			<h1>Создание/редактирование</h1>
			<form:form action="saveShop" method="post" modelAttribute="shop">
				<table>
					<form:hidden path="id"/>
					<tr>
						<td>Имя:</td>
						<td><form:input path="name"/></td>
					</tr>
					<tr>
						<td align="center" style="text-align: left">
							<a href="/" class="btn">На главную</a>
						</td>
						<td align="center" style="text-align: right">
							<input class="btn" type="submit" value="Save">
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</body>
</html>