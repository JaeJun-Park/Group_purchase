	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
	<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/custom.css">
	<title>home</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type = "text/javascript" src="./js/bootstrap.js"></script>
</head>
<body>
	<h1>mainpage</h1>
	<h1>${isLogin}</h1>
	<main>
		<nav id="acount-menu">
			<h1 class="hidden">회원메뉴</h1>
            <ul>
            	<li>
            		<a href="./mypage?f=studentNum&v=201701">mypage</a>
	            </li>
	            <c:choose>
	            	<c:when test="${isLogin}">
	            		<li><a href="./login?c=out">로그아웃</a></li>
	            	</c:when>
	            	<c:when test="${!islogin}">
	            		<li><a href="./login">로그인</a></li>
	            		<li><a href="./signup">회원가입</a></li>
	            	</c:when>
	            </c:choose>
            </ul> 
		</nav>
		
	
	</main>
	
	
	
	<% 
		String messageContent = null;
		if(session.getAttribute("messageContent") != null)
		{
			messageContent = (String) session.getAttribute("messageContent");
		}
		String messageType = null;
		if(session.getAttribute("messageType") != null)
		{
			messageType = (String) session.getAttribute("messageType");
		}
		if(messageContent != null) {
	%>
	<div class = "modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success");%>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">
							<%= messageType %>
						</h4>
					</div>
					<div class="modal-body">
						<%= messageContent %>
					</div>
					<div class="modal-footer">
						<button type="button" class = "btn btn-primary" data-dismiss="modal">확인</button>
					</div> 	
				</div>
			</div>	
		</div>
	</div>
	<script>
	  	$(document).ready(function() {
	  		$('#messageModal').modal("show");
		  });
	</script>
	<%
		session.removeAttribute("messageContent");
		session.removeAttribute("messageType");
		}
	%>
</body>
</html>