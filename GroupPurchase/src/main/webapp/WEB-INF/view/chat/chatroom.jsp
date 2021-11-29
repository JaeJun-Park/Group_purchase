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
	<title>chat</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type = "text/javascript" src="./js/bootstrap.js"></script>
	<script type="text/javascript">
		function autoClosingAlert(selector, delay) {
			var alert = $(selector).alert();
			alert.show();
			window.setTimeout(function() {alert.hide()}, delay);
		}
		function submitFunction()
		{
			var studentNum = ${studentNum};
			var roomNum = ${roomNum};
			var chatContent = $('#chatContent').val();
			$.ajax({
				type: "POST",
				url: "./ChatSubmitServlet",
				data: {
					studentNum: studentNum,
					roomNum: roomNum,
					chatContent: encodeURIComponent(chatContent)
				},
				success: function(result) {
					if(result == 1)
						autoClosingAlert('#successMessage', 2000);
					else if(result == 0)
						autoClosingAlert('#dangerMessage', 2000);
					else
						autoClosingAlert('#warningMessage', 2000);
				}
			});
			$('#chatContent').val('');
		}
		var lastMessageNum = 0;
		function chatListFunction(type) {
			var studentNum = ${studentNum};
			var roomNum = ${roomNum};
			$.ajax({
				type: "POST",
				url: "./ChatListServlet",
				data: {
					studentNum: studentNum,
					roomNum: roomNum,
					listType: type
				},
				success: function(data) {
					if(data == "") return;
					var parsed = JSON.parse(data);
					var result = parsed.result;
					$('#chatList').html("");
					for(var i = 0; i<result.length; i++){
						if(result[i][0].value == studentNum){
							result[i][0].value = '나';	
						}	
						addChat(result[i][0].value, result[i][2].value, result[i][3].value);
					}
					lastMessageNum = Number(parsed.last);
				}
			});
		}
		function addChat(chatName, chatContent, chatTime) {

			$('#chatList').append('<div class="row">' + 
					'<div class= "col-lg-12">' +
					'<div class= "media">' +
					'<a class="pull-left" href=\'./profile?studentNum='+chatName+ '\'>' + 
					'<img class="media-object img-circle" style="width: 30px; height: 30px;" src="images/icon.png" alt = "">' +
					'</a>' +
					'<div class= "media-body">' +
					'<h4 class="media-heading">' +
					chatName +
					'<span class="small pull-right">' + 
					chatTime +
					'</span>' +
					'</h4>' +
					'<p>' +
					chatContent +
					'</p>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'<hr>');
			$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
		}
		function getInfiniteChat() {
			setInterval(function() {
				chatListFunction(lastMessageNum);
			}, 2000);	
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
				<li class="active"><a href="chatpage">메시지함</a></li>
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
	<div class="container bootstrap snippet">
			<div class="row">
				<div class="col-xs-12">
					<div class="portlet portlet-default">
						<div class="portlet-heading">
							<div class="portlet-title">
								<h4><i class="fa fa-circle text-green"></i>${roomNum}</h4>
							</div>
							<div class="clearfix"></div>
						</div>
						<div id="chat" class="panel-collapse collapse in">
							<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto; width: auto; height: 600px;">
							</div>
							<div class="portlet-footer">
								<div class="row" style="height:120px;">
									<div class="form-group col-xs-10">
										<textarea style="height: 110px;" id="chatContent" class="form-control" placeholder="메시지를 입력하세요." maxlength="255"></textarea>
									</div>
									<div class="form-group col-xs-2">
										<button type="button" class ="btn btn-default pull-right" onclick="submitFunction()">전송</button>
										<div class="clearfix"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="alert alert-success" id="successMessage" style="display: none;">
			<strong>메시지 전송에 성공했습니다.</strong>
		</div>
		<div class="alert alert-danger" id="dangerMessage" style="display: none;">
			<strong>이름과 내용을 모두 입력해주세요.</strong>
		</div>
		<div class="alert alert-warning" id="warning" style="display: none;">
			<strong>데이터베이스 오류가 발생했습니다.</strong>
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
				chatListFunction('10');
		  		getInfiniteChat();
			  });
		</script>
</body>
</html>