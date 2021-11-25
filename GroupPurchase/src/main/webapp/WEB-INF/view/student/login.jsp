<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<main>
		<form action="./login" method="post">
        	<fieldset>
	            <legend>로그인</legend>
	            <input type="text" name="id" placeholder="아이디(ID)"><br>
	            <input type="password" name="pw" placeholder="패스워드(PW)"><br>
	            <button type="submit">로그인</button>
        	</fieldset>
        </form>
	</main>
</body>