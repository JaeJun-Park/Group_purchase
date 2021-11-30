<%@ page import="gp.web.entity.Post" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<!-- --------------------------- <body> --------------------------------------- -->
<div id="body">
    <div class="content-container clearfix">

        <!-- --------------------------- main --------------------------------------- -->


        <main class="main">
            <h2 class="main title">게시판</h2>

            <div class="search-form margin-top first align-right">
                <h3 class="hidden">게시글 검색폼</h3>
                <form class="table-form">
                    <fieldset>
                        <legend class="hidden">게시글 검색 필드</legend>
                        <label class="hidden">검색분류</label>
                        <select name="f">
                            <option ${param.f == "title"?"selected":""} value="title">제목</option>
                            <option ${param.f == "studentNum"?"selected":""} value="studentNum">작성자</option>
                        </select>
                        <label class="hidden">검색어</label>
                        <input type="text" name="q" value="${param.q}"/>
                        <input class="btn btn-search" type="submit" value="검색"/>
                    </fieldset>
                </form>
            </div>

            <div class="notice margin-top">
                <h3 class="hidden">게시글 목록</h3>
                <table class="table">
                    <thead>
                    <tr>
                        <th class="w60">번호</th>
                        <th class="expand">제목</th>
                        <th class="w100">작성자</th>
                        <th class="w100">작성일</th>
                        <th class="w60">조회수</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="post" items="${list}" varStatus="st">
                        <tr>
                            <td>${post.postNum}
                            </td>
                            <td class="title indent text-align-left">
                                <a
                                        href="detail?postNum=${post.postNum}">${post.title}
                                </a></td>

                            <td>${post.studentNum}
                            </td>
                            <td>
                                <fmt:formatDate pattern="yy-MM-dd hh:mm" value="${post.date}"/>
                            </td>
                            <td>${post.numOfParticipants}/${post.limitOfParticipants}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <c:set var="page" value="${(empty param.p)?1:param.p}"/> <%--setAttribute--%>
            <c:set var="startNum" value="${page-(page-1)%5}"/>
            <c:set var="lastNum" value="${fn:substringBefore(Math.ceil(count/15),'.')}"/>

            <div class=" indexer margin-top align-right">
                <h3 class="hidden">현재 페이지</h3>
                <div><span class="text-orange text-strong">${(empty param.p)?1:param.p}</span>/ ${lastNum} pages</div>
            </div>

            <% if (((boolean)session.getAttribute("isLogin"))) { %>
            <div>
                <a class="btn-text btn-default" href="reg">글쓰기</a>
            </div>
            <% } %>

            <div class="margin-top align-center pager">
                <div>
                    <c:if test="${startNum-5 >= 1}">
                        <a href="?p=${startNum-5}&f=${param.f}&q=${param.q}" class="btn btn-next">이전</a>
                    </c:if>
                    <c:if test="${startNum-5 < 1}">
                        <span class="btn btn-prev" onclick="alert('이전 페이지가 없습니다.');">이전</span>
                    </c:if>
                </div>
                <ul class="-list- center">
                    <c:forEach var="i" begin="0" end="4">
                        <c:if test="${startNum+i <= lastNum}">
                            <li><a class="-text- ${(page==startNum+i)?'oran  ge':''} bold"
                                   href="?p=${startNum+i}&f=${param.f}&q=${param.q}">${startNum+i}</a></li>
                        </c:if>
                    </c:forEach>
                </ul>
                <div>
                    <c:if test="${startNum+5 <= lastNum}">
                            <a href="?p=${startNum+5}&f=${param.f}&q=${param.q}" class="btn btn-next">다음</a>
                    </c:if>
                    <c:if test="${startNum+5 > lastNum}">
                        <span class="btn btn-next" onclick="alert('다음 페이지가 없습니다.');">다음</span>
                    </c:if>
                </div>

            </div>
        </main>


    </div>
</div>

</body>

</html>