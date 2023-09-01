<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="./layout/header.jsp"%>

<div class="container-fluid mt-3">
	<c:if test="${!empty postList }">
		<p>${ postList.totalPages }</p>
		<br>
		<div class="card">
		<c:forEach var="post" items="${ postList.content }">
			<div class="card-body">
				<h4 class="card-title">${ post.title }</h4>
				<a href="#" class="btn btn-secondary card-link">상세보기</a>
			</div>
		</c:forEach>
		</div>
		
		<br>
		<ul class="pagination justify-content-center">
			<!-- 이전 -->
			<c:choose>
				<c:when test="${ postList.number == 0 }">
				  	<li class="page-item disabled"><a class="page-link" href="?page=${ postList.number - 1 }">Previous</a></li>
				</c:when>
				<c:otherwise>
				  	<li class="page-item"><a class="page-link" href="?page=${ postList.number - 1 }">Previous</a></li>
				</c:otherwise>
			</c:choose>
			
			<!-- 5페이지까지 보이기 -->
			<c:choose>
				<c:when test="${ postList.totalPages <= 5 }">
					<li class="page-item"><a class="page-link" href="?page=${ postList.number + 1 }">5페이지 이하</a></li>
				</c:when>
				<c:otherwise>
					<!-- 5페이지 이상일 경우 -->
					<c:forEach var="page" begin="0" end="${ postList.totalPages - 1 }">
						<!-- 현재 페이지 표시 -->
						<c:choose>
							<c:when test="${ postList.number == page }">
								<li class="page-item active"><a class="page-link" href="?page=${ page }">${ page + 1 }</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link" href="?page=${ page }">${ page + 1 }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
			<!-- 다음 -->
			<c:choose>
				<c:when test="${ postList.number == (postList.totalPages - 1) }">
					<li class="page-item disabled"><a class="page-link" href="?page=${ postList.number + 1 }">Next</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" href="?page=${ postList.number + 1 }">Next</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</c:if>
</div>

<%@ include file="./layout/footer.jsp" %>