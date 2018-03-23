<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>

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
			<h1>Список магазинов</h1>
			
			<table border="1">
				<tbody>
				<tr>
					<th>id</th>
					<th>Имя</th>
					<th>Действие</th>
				</tr>
				
				<c:forEach var="shop" items="${list}">
					<tr>
						<td>${shop.id}</td>
						<td>${shop.name}</td>
						<td>
							<a href="editShop?id=${shop.id}" class="btn">Изменить</a>
							&nbsp;
							<a href="deleteShop?id=${shop.id}" class="btn">Удалить</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<h4>
				Добавить <a href="newShop" class="btn">магазин</a>
			</h4>
		</div>
	</body>
</html>