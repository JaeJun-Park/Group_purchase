<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>home</title>
</head>
<body>
	<h1>mainpage</h1>
	<main>
		<nav id="acount-menu">
			<h1 class="hidden">회원메뉴</h1>
            <ul>
            	<li>
            		<a href="./mypage?f=studentNum&v=201701">mypage</a>
	            </li>
                <li><a>로그인</a></li>
                <li><a>회원가입</a></li>
            </ul>
		</nav>
		
		
		<form action="./mypage">
        	<fieldset>
	            <legend>과정검색필드</legend>
	            <label>과정검색</label>
	            <input type="text" name="f" value="asd" />
	            <input type="submit" value="검색" />
        	</fieldset>
        </form>
	</main>
</body>
</html>