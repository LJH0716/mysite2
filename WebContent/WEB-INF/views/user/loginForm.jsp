<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo" %>

<%
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		System.out.println(authUser);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet"
	type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id="wrap">

		<div id="header" class="clearfix">
			<h1>
				<a href="/mysite2/main">MySite</a>
			</h1>

			<%if(authUser == null){%>   <!-- 로그인 실패, 로그인전 -->
			<ul>
				<li><a href="/mysite2/user?action=loginForm" class="btn_s">로그인</a></li>
				<li><a href="/mysite2/user?action=joinForm" class="btn_s">회원가입</a></li>
			</ul>
			<%}else {%> <!-- 로그인 성공 -->
			<ul>
				<li><%=authUser.getName() %> 님 안녕하세요^^</li>
				<li><a href="/mysite2/user?action=logout" class="btn_s">로그아웃</a></li>
				<li><a href="/mysite2/user?action=modify" class="btn_s">회원정보수정</a></li>
			</ul>
			
			<%}%>

		</div>
		<!-- //header -->

		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>회원</h2>
				<ul>
					<li>회원정보</li>
					<li>로그인</li>
					<li>회원가입</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head">
					<h3>로그인</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">로그인</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->

				<div id="user">
					<div id="loginForm">
						<form action="/mysite2/user" method="get">
							<input type="text" name="action" value="login">
							<!-- 아이디 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> 
								<input
									type="text" id="input-uid" name="id" value=""
									placeholder="아이디를 입력하세요">
							</div>

							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">비밀번호</label> <input
									type="text" id="input-pass" name="password" value=""
									placeholder="비밀번호를 입력하세요">
							</div>


							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">로그인</button>
							</div>

						</form>
					</div>
					<!-- //loginForm -->
				</div>
				<!-- //user -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->

		<div id="footer">Copyright ⓒ 2022 이정화. All right reserved</div>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>O