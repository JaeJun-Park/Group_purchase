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
	<link rel="stylesheet" href="./css/bootstrap.css">
	<link rel="stylesheet" href="./css/custom.css">
	<title>review</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type = "text/javascript" src="./js/bootstrap.js"></script>
	<script type="text/javascript">
			function getReviewListFunction() {
			var studentNum = ${studentNum}
			$.ajax({
				type: "POST",
				url: "./getReceivedReviewList",
				data: {
					studentNum: studentNum
				},
				success: function(data){
					if(data == "") return;
					var parsed = JSON.parse(data);
					var result = parsed.result;
					$('#reviewTable').html(''); 
					for(var i = 0; i < result.length; i++)
					{
						addReview(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value)
					}
				}
			})
		}
		function addReview(reviewNum, rating, comment, evaluateeNum) {
			$('#reviewTable').append('<tr onclick="location.href=\'./detailReview?reviewNum=' + reviewNum+ '&evaluateeNum='+ evaluateeNum + '\'">' + 
					'<td style = "width: 150px;"><h5>' + rating + '</h5></td>' +
					'<td>' +
					'<h5>' + comment + '</h5>' +
					'</td>' +
					'</tr>');
		}
	</script>
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
				<li><a href="post/mylist">참여 내역</a>
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
	<c:choose>
		<c:when test="${test}">
			<div class="container">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<thead>
						<tr>
							<th colspan = "3"><h4>작성가능한 리뷰</h4></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><h5>갯수</h5></td>
							<td><h5>${count}</h5></td>
							<td onclick="location.href ='./reviewlistForWrite'">
			                     <h5>목록 자세히 보기</h5>
		                    </td>
						<tr>					
					</tbody>
				</table>
			</div>
		</c:when>
	</c:choose>
	
	
	<div class="container">
		<table class = "table" style="margin: 0 auto;">
			<thead>
				<tr>
					<th><h4>받은 리뷰 평균: ${avg}</h4></th>
				</tr>
			</thead>
			<div style="overflow-y: auto; width: 100%; maxheight: 450px;">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<tbody id = "reviewTable"></tbody>
				</table>
			</div>
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
		<script type="text/javascript">
			$(document).ready(function() {
				getReviewListFunction();
			  });
			
		
		</script>
</body>
</html>