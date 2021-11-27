<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/custom.css">
	<title>로그인</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type = "text/javascript" src="./js/bootstrap.js"></script>
	<title>Login</title>
</head>
<body>
	<main>
		<div class="container">
			<form action="./login" method="post">
				<table class="table table-borded table-hover" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<th colspan="3"><h4>로그인</h4></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="width: 120px;"><h5>아이디</h5></td>
							<td	colspan="2"><input class="form-control" type="text" id="id" name="id" maxlength="20" placeholder="아이디(ID)"></td>
						</tr>
						<tr>
							<td style="width: 120px;"><h5>비밀번호</h5></td>
							<td colspan="2"><input class="form-control" type="password" id="pw" name="pw" maxlength="20" placeholder="패스워드(PW)"></td>
						</tr>		
						<tr>
							<td style="text-align: right" colspan="3"><h5 style="color: red;"></h5><input class ="btn btn-primary pull-right" type="submit" value="로그인"></td>
						</tr>					
					</tbody>	
				</table>
			</form>
			<form method="get" action="./home">
				<div class="container">
					<h5 style="color: red;"></h5><input class ="btn btn-primary pull-right" type="submit" value="홈으로">
				</div>
			</form>
		</div>
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



