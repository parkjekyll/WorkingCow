<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WorkingCow | 공지사항</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="./css/notice.css">
</head>
<body>
	<div class="container_top"> 
		<!-- navbar -->
		<%@ include file="../common/navbar.jsp" %>
		<div class="subpageBar">
			<ul>
				<li class="on"> <a href="notice">공지사항</a> </li>
				<li> <a href="FAQs">FAQs</a></li>
				<li> <a href="inquiryToAdmin">문의게시판</a></li>
			</ul>
		</div>
	</div>
	<!-- sidebar && notice board  -->
	<div class="container_bottom">
		<!-- 사이드바로 이동한 목록 출력 -->
		<div class="noticeBanner">
			<img src="./images/notice.jpg">
		</div>
		<div class="main">
				<div class="noticeBoard">
					<table class="noticeTable">
					<colgroup>
						<col width="110px">
						<col width="614px">
						<col width="127px">
						<col width="127px">
					</colgroup>
						<tr>
							<th>No</th>
							<th>제목</th>
							<th>작성자</th>
							<th>등록일</th>
						</tr>
					<c:forEach items="${noticeList }" var="list" >
						<tr>
							<td class="noticeNo">${list.nboard_no }</td>
							<td class="noticeTitle"><span>[공지사항]</span><a href="noticeDetail?bno=${list.nboard_no}">${list.nboard_title }</a></td>
							<td class="adminName">${list.admin_name }</td>
							<td class="noticeDate">${list.nboard_date }</td>
						</tr>
					</c:forEach>
					</table>
					
					<div class="paging">
						<!-- 전체페이지 -->
						<fmt:parseNumber integerOnly="true" value="${totalCount/20 }"
							var="totalPage" />
						<c:if test="${(totalCount%20) > 0 }">
							<c:set value="${totalPage + 1 }" var="totalPage" />
						</c:if>
						<!-- 시작페이지 -->
						<c:if test="${page%5 ne 1 }">
							<fmt:parseNumber integerOnly="true" value="${((page-1) / 5) }"
								var="startPage" />
							<c:set var="startPage" value="${startPage * 5 + 1 }" />
						</c:if>
						<c:if test="${page%5 eq 1 }">
							<c:set var="startPage" value="${page}" />
						</c:if>
						<!-- 마지막페이지 -->
						<c:set var="endPage" value="${startPage + 4 }" />
						<c:if test="${startPage + 4 gt totalPage }">
							<c:set var="endPage" value="${totalPage }" />
						</c:if>
						<c:if test="${page > 5 }">
							<a href="notice?page=${startPage-5 }"> ‹ </a>
						</c:if>
						<c:if test="${page < 6 && page > 1}">
							<a href="notice?page=1"> ‹ </a>
						</c:if>
						<c:forEach begin="${startPage }" end="${endPage }" var="i">
							<c:if test="${page eq i }">
								<strong>${i }</strong>
							</c:if>
							<c:if test="${page ne i }">
								<a href="notice?page=${i }"><span>${i }</span></a>
							</c:if>
						</c:forEach>
						<c:if test="${startPage+5 < totalPage }">
							<a href="notice?page=${startPage+5 }"> › </a>
						</c:if>
						<c:if test="${startPage+5 > totalPage && page < totalPage}">
							<a href="notice?page=${totalPage }"> › </a>
						</c:if>
					</div>
				</div>
				<div class="noticeSearchForm">
					<c:if test="${!empty sessionAdmin }">
						<button type="button" class="btn btn-primary" onclick="location.href='./noticeWrite'">글쓰기</button>
					</c:if>
				</div>
			</div>
		</div>
	</div>
		<footer class="">
			<%@include file="../common/footer.jsp" %>
		</footer>
</body>
</html>