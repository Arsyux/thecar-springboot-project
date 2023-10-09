<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<br>

<!-- login.css -->
<link rel="stylesheet" type="text/css" href="/css/login.css">

<div class="container mt-5 mb-5">
	<!--
		스프링 시큐리티에 인증을 요청하기 위해 form 태그의 action 속성을 /auth/securitylogin로 지정하고 요청 방식은 post로 설정한다.
		여기서 중요한 것은 스프링 시큐리티가 사용자 정보를 추출할 때 id 속성을 인지하지 못하므로 기존의 id 속성 이름을 name으로 변경한다.	
	-->
	<!-- Logo -->
	<div class="row mb-5">
		<div class="col-sm-0 col-md-4"></div>
		<div class="col-sm-12 col-md-4">
			<img class="w-100" src="/image/main/logo.png">
		</div>
		<div class="col-sm-0 col-md-4"></div>
	</div>
	
	<c:if test="${error != null}">
		<h4 class="mb-5" style="text-align: center; font-weight: bold; color: #333333;">${exception}</h4>
	</c:if>

	<!-- 영역 제한 -->
	<div class="row">
		<div class="col-sm-0 col-md-2 col-lg-4"></div>
		<div class="col-sm-0 col-md-8 col-lg-4">

			<div class="row mb-3" style="border-style: solid; border-color: #DDDDDD; border-radius: 5px; border-width: 1px;">
				<form action="/auth/securitylogin" method="post">
					<!--
						JSP 파일에서 다국어를 출력할 때는 spring:message 태그를 사용하며
						code 속성의 속성값으로 메시지 파일에 등록된 메시지의 키를 등록한다.
					-->
					<!-- ID -->
					<div class="input-group mt-3 mb-3">
						<span class="input-group-text"><i class="bi bi-person"></i></span> <input type="text" class="form-control" name="username" placeholder="아이디"></input>
					</div>

					<!-- Password -->
					<div class="input-group mb-3">
						<span class="input-group-text"><i class="bi bi-lock"></i></span> <input type="password" class="form-control" name="password" placeholder="비밀번호"></input>
					</div>
					<div class="row mb-3">
						<div class="col-12">
							<button id="btn-login" class="btn btn-dark w-100">
								<spring:message code="user.login.form.login_btn" />
							</button>
						</div>
					</div>

				</form>
			</div>

			<div class="row mb-3">
				<div class="col-12" style="text-align: center;">
					<a class="logintag" href="/auth/findUsername" style="border-right: solid; border-width: 1px; border-color: #555555; padding-right: 10px; padding-left: 10px;">아이디 찾기</a> <a class="logintag"
						href="/auth/findPassword" style="border-right: solid; border-width: 1px; border-color: #555555; padding-right: 10px; padding-left: 10px;">비밀번호 찾기</a> <a class="logintag" href="/auth/insertUser"
						style="padding-right: 10px; padding-left: 10px;">회원가입</a>
				</div>
			</div>

		</div>

		<div class="col-sm-0 col-md-2 col-lg-4"></div>

	</div>

</div>

<!-- 스프링 시큐리티 적용에따라 주석 처리하였다.
<script src="/js/login.js"></script>
-->

<%@ include file="../layout/footer.jsp" %>