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
	<title>Review</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type = "text/javascript" src="./js/bootstrap.js"></script>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>	
			</button>
			<a class="navbar-brand" href="home">KNU공동구매</a>
		</div>
		<div class = "collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="home">메인</a></li>
				<li><a href="post/list">게시판</a></li>
				<li><a href="chatpage">메시지함</a></li>
				<li class="active"><a href="receivedReview?studentNum=${loginNum}">리뷰</a><li>
			</ul>
			<c:choose>
				<c:when test="${isLogin}">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-haspopup="true"
								aria-expanded="false">마이페이지<span class="caret"></span>
							</a>		
							<ul class="dropdown-menu">
								<li><a href="./mypage">프로필</a>
	            				<li><a href="./update">회원정보수정</a></li>
	            				<li><a href="./login?c=out">로그아웃</a></li>
	            				
	            			</ul>
	            		</li>
	            	</ul>			
            	</c:when>
            	<c:when test="${!islogin}">
		            <ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-haspopup="true"
								aria-expanded="false">접속하기<span class="caret"></span>
							</a>
							<ul class="dropdown-menu">
	            				<li><a href="./login">로그인</a></li>
		            			<li><a href="./signup">회원가입</a></li>
	            			</ul>		
	            		</li>
	            	</ul>			
            	</c:when>
	    	</c:choose>
		</div>
	</nav>
	<div class="container">
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="4"><h4>${review.getReviewNum()}</h4></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width: 120px;"><h5>작성자</h5></td>
					<td style="width: 300px;">
						<h5>${review.getWriterNum()}</h5>
						<input type="hidden" name="writerNum" value="${review.getWriterNum()}">
					</td>
					<td style="width: 120px;"><h5>대상</h5></td>
					<td style="width: 300px;">
						<h5>${review.getEvaluateeNum()}</h5>
						<input type="hidden" name="evaluateeNum" value="${review.getEvaluateeNum()}">
					</td>
				</tr>
				<tr>
					<td style="width: 120px;"><h5>내용</h5></td>
					<td colspan="3">
						<h5>${review.getComment()}</h5>
						<input type="hidden" name="comment" value="${review.getComment()}">
					</td>
				</tr>
				<tr>
					<td style="width: 120px;"><h5>평점</h5></td>
					<td style="width: 300px;">
						<h5>${review.getRating()}</h5>
						<input type="hidden" name="rating" value="${review.getRating()}">
					</td>
					<td style="width: 120px;"><h5>시간</h5></td>
					<td style="width: 300px;">
						<h5>${review.getTime()}</h5>
						<input type="hidden" name="time" value="${review.getTime()}">
					</td>
				</tr>						
			</tbody>	
		</table>
	</div>
	
	
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