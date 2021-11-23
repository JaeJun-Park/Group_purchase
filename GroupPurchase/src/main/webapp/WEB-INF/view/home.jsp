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
        		<c:if test="${isLogin == true}">
           			<li><a href="./logout">로그아웃</a></li>
				</c:if>
				<c:if test="${islogin == false}">
           			<li><a href="./login">로그인</a></li>
				</c:if>
                <li><a>회원가입</a></li>
            </ul> 
		</nav>
		
	
	</main>
</body>
</html>