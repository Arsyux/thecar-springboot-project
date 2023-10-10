<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<br>

<!-- login.css -->
<link rel="stylesheet" type="text/css" href="/css/login.css">

<div class="container mt-5 mb-5">

	<!-- Logo -->
	<div class="row">
		<div class="col-sm-0 col-md-4"></div>
		<div class="col-sm-12 col-md-4">
			<img class="w-100" src="/image/main/logo.png">
		</div>
		<div class="col-sm-0 col-md-4"></div>
	</div>
	
	<h3 align="center" style="font-weight: bold;">로그인</h3>

	<c:if test="${error != null}">
		<h4 class="mt-3 mb-3" style="text-align: center; font-weight: bold; color: #333333;">${error}</h4>
		<h4 class="mt-3 mb-3" style="text-align: center; font-weight: bold; color: #333333;">${exception}</h4>
	</c:if>
	<c:if test="${ error == null }">
		<h4 class="mt-3 mb-3" style="text-align: center; font-weight: bold; color: #333333;">ㅇㅅㅇ</h4>

	</c:if>

	<!-- 영역 제한 -->
	<div class="row mt-3">
		<div class="col-sm-0 col-md-2 col-lg-4"></div>
		<div class="col-sm-0 col-md-8 col-lg-4">

			<div class="row mb-3" style="border-style: solid; border-color: #DDDDDD; border-radius: 5px; border-width: 1px;">
				<form action="/auth/securitylogin" method="post">
					
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

<%@ include file="../layout/footer.jsp" %>