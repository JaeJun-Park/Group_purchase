
<%@ page import="java.util.List" %>
<%@ page import="gp.web.entity.Locker" %>
<%@ page import="gp.web.entity.Join" %>
<%@ page import="gp.web.entity.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    HttpSession httpSession = request.getSession();
    String loginNum = (String) httpSession.getAttribute("loginNum");
    Post post = (Post) request.getAttribute("post");
    List<Join> joinList = (List<Join>) request.getAttribute("joinList");
    boolean isJoined = false;%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="../css/bootstrap.css">
	<link rel="stylesheet" href="../css/custom.css">
	<title>home</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script type = "text/javascript" src="../js/bootstrap.js"></script>
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
			<a class="navbar-brand" href="../home">KNU공동구매</a>
		</div>
		<div class = "collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="../home">메인</a></li>
				<li class="active"><a href="../post/list">게시판</a></li>
				<li><a href="../chatpage">메시지함</a></li>
				<li><a href="../receivedReview?studentNum=${loginNum}">리뷰</a><li>
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
								<li><a href="../mypage">프로필</a>
	            				<li><a href="../update">회원정보수정</a></li>
	            				<li><a href="../login?c=out">로그아웃</a></li>
	            				
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
	            				<li><a href="../login">로그인</a></li>
		            			<li><a href="../signup">회원가입</a></li>
	            			</ul>		
	            		</li>
	            	</ul>			
            	</c:when>
	    	</c:choose>
		</div>
	</nav>
<div id="body">
    <div class="content-container clearfix">

        <!-- --------------------------- main --------------------------------------- -->


        <main>
            <h2 class="main title">게시판</h2>

            <div class="margin-top first">
                <h3 class="hidden">게시글 내용</h3>
                <table class="table">
                    <tbody>
                    <tr>
                        <th>제목</th>
                        <td class="text-align-left text-indent text-strong text-orange"
                            colspan="3">${post.title}
                        </td>
                    </tr>
                    <tr>
                        <th>작성일</th>
                        <td class="text-align-left text-indent" colspan="3"><fmt:formatDate pattern="yy-MM-dd hh:mm:ss"
                                                                                            value="${post.date}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>작성자</th>
                        <td>${post.studentNum}
                        </td>
                        <th>참가인원/정원</th>
                        <td>${post.numOfParticipants}/${post.limitOfParticipants}
                        </td>
                    </tr>
                    <tr class="content">
                        <td colspan="4">
                            <div><a href=${post.productInfo}>${post.productInfo}</a>
                            </div>
                            <div><br></div>
                            <div><br></div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <div>
                                <%
                                    Locker locker = (Locker) request.getAttribute("locker");
                                    if (post.getStudentNum().equals(loginNum)) // writer
                                    {
                                        if (post.getState().equals("ordering")) {
                                            out.println("Locker No." + locker.getLockerNum());
                                            out.print("password: " + locker.getPassword());
                                        }
                                    }
                                %>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--            if (((String) session.getAttribute("loginNum")).equals(post.getStudentNum())) {--%>
            <div class="margin-top text-align-center">
                <a class="" href="list">list</a>
                <%
                    if (loginNum != null) // login
                    {
                        for (Join join : joinList) {
                            if (join.getStudentNum().equals(loginNum)) // joined
                            {
                                isJoined = true;
                                out.print("<a class=\"\" href=\"../chatroom?roomNum=" + post.getPostNum() + "\">ChatRoom</a>\n"); // not yet
                                if (post.getStudentNum().equals(loginNum)) // writer
                                {
                                    if (post.getState().equals("full")) {
                                        out.println("<a class=\"\" href=\"state?postNum=" + post.getPostNum() + "&toState=ordering\">모집 종료</a>");// @@
                                    } else if (post.getState().equals("ordering")) {
                                        out.print("<a href=\"state?postNum=" + post.getPostNum() + "&take=T\">Take</a>"); // @@
                                    }
                                    out.println("<a class=\"\" href=\"edit?postNum=" + post.getPostNum() + "\">edit</a>");
                                    out.print("<a class=\"\" href=\"delete?postNum=" + post.getPostNum() + "\">delete</a>");
                                } else { // 글쓴이 외 참가자
                                    if (!post.getState().equals("ordering"))
                                        out.print("<a href=\"disjoin?postNum=" + post.getPostNum() + "\">disJoin</a>");
                                    else {
                                        out.print("<a href=\"state?postNum=" + post.getPostNum() + "&take=T\">Take</a>"); // @@
                                    }
                                }
                                break;
                            }
                        }

                        if (!isJoined && (post.getNumOfParticipants() < post.getLimitOfParticipants())) { // login은 됬으나 join은 되지 않은 넘덜
                            out.print("<a class=\"\" href=\"join?postNum=" + post.getPostNum() + "\">Join</a>");
                        }
                    }
                %>
            </div>

            <div class="margin-top text-align-center">
            </div>



        </main>

    </div>
</div>

<!-- ------------------- <footer> --------------------------------------- -->
</body>

</html>