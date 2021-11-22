<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Mypage</title>
</head>
<body>
	<h1>mypage</h1>
	<main>
		<div>
			<h3>내용</h3>
			<table class = "table">
				<tr>
					<th>학번</th>
					<td class="text-align-left text-indent" colspan="3">${s.studentNum}</td>
				</tr>
			</table>
		</div>
	
	</main>
</body>
</html>