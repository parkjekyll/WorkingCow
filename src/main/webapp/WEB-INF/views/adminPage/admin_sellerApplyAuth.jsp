<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<script src="./js/page.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="./css/paging.css">

<meta charset="UTF-8">
<title>관리자 페이지</title>
<style>
*{margin: 0; padding: 0;}
head{margin: 0;	padding: 0;	margin-left: 20px;}
html, body {margin: 0; padding: 0;}
ul li {list-style: none;}

.emp {vertical-align: middle;	margin-left: 50px; float: left;}
.emp, .empother {margin-top: 40px;}
.empname {padding-left: 30px; padding-top: 80px; vertical-align: middle; padding-right: 100px; width: 25%; float: left;}
.empother{	float: left; vertical-align: middle; position: relative; right: -300px;}

.header {margin: 0; padding: 0;	min-width: 1903px; width: 100%; height: 250px; background-color: black;}
.container{width: 100%; height: 100%; margin: 0; padding: 0; min-width: 1903px; min-height: 1100px;}

.sidebar {padding-top: 20px;	width: 15%;	height: 100%; margin-left: 40px; position: relative; top: 0; float: left;}
.sidebar ul {text-align: left;	padding: 0;}
.sidebar ul li {text-decoration: none;	padding-bottom: 20px;}
.sidebar ul li a:active,.sidebar ul li a:hover {font-weight: bold;	text-decoration: none;}

.side,.side_on {text-decoration: none; color: black;}
.side_on {font-weight: 900; font-size: 18px;}

.main {padding-top: 20px; float: right;	position: relative;	width: 82%;	min-height:1100px; height: 100%; border-left: 1px solid black;	padding-left : 20px;}
.main ul {display: block;}
.main ul li {border: 1px solid lightgray; margin-left: 10px; text-align: center; float: left;}
.main h3{float: left;}
.main_menudetail {width: 100%; min-width: 1539px; height: 38px;}
.che {float: left; padding-right: 300px; text-align: center;}
.che_name, .che_data {text-align: center; padding-bottom: 15px; color: white;}

.detail {width: 100%; text-align: center; min-width: 1539px;} 
.detail tr {border-bottom: 1px solid lightgray;}
.detail tr:first-child {border-bottom: 3px solid lightgray;}
.detail th {padding-bottom: 20px;}
.detail td {text-align: center;}

.pay {float: left; width: 60%; margin-top: 40px; border: 2px solid lightgray;}
.calender input{height: 35px;}
.main button{position: relative; left: 10px; float: right; margin-right: 60px;}
#memberSearch {width: 200px; height: 40px; float: right; border: 1px solid lightgray; border-radius: 7px;}
.paging {margin: 0;}
</style>
</head>
<body>
	<!-- navbar -->
	<%@ include file="../common/navbar.jsp" %>
	<div class="header">
		<div class="head" style="color: white; font-weight: bold;">Admin Page</div>

		<%@ include file="./admin_account.jsp" %>
	</div>
	<!-- 사이드바를 이용해 목록 이동 가능 -->
	<div class="container">
		<%@ include file="./admin_sidebar.jsp" %>
	
		<!-- 사이드바로 이동한 목록 출력 -->
		<div class="main">
			<div class="main_menudetail">
				<h3>판매자 승인 관리</h3>
				<button class ="btn btn-primary">조회</button>
				<input type="text" placeholder="회원 검색" id="memberSearch">
			</div>
			<hr>
	
	
			<div class="main_detail">
				<table class="detail">
					<tr>
						<th style="width: 50px;">신청 번호</th>
						<th style="width: 50px;">멤버번호</th>
						<th style="width: 100px;">이메일 주소</th>
						<th style="width: 50px;">닉네임</th>
						<th style="width: 100px;">회원 구분</th>
						<th style="width: 50px;">제출 서류보기</th>

					</tr>
					
					<c:forEach items="${result}" var="data">
					<tr style="height: 100px;">
						<td style="width: 50px;">${data.sellerAuthVO.seller_auth_no }</td>
						<td style="width: 50px;">${data.customerVO.customer_no }</td>
						<td style="width: 100px;">${data.customerVO.customer_email }</td>
						<td style="width: 50px;">${data.customerVO.customer_nick }</td>
						
						<td style="width: 100px;">
						<c:if test="${data.customerVO.customer_grade eq 2 }" >판매자 신청중</c:if>
						<c:if test="${data.customerVO.customer_grade eq 3 }" >전환 완료</c:if>
						</td>
						
						<td style="width: px;">
						<c:if test="${data.customerVO.customer_grade eq 2 }" ><button class="btn btn-primary btn-sm" onclick="location.href='${pageContext.request.contextPath}/checkSellerApplyDocByAdmin.do?seller_auth_no=${data.sellerAuthVO.seller_auth_no }&customer_no=${data.customerVO.customer_no }'">서류 보기</button></c:if>
						<c:if test="${data.customerVO.customer_grade eq 3 }" ><button class="btn btn-primary btn-sm" disabled>전환완료</button></c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<div class="paging">
					<!-- 전체페이지 -->
					<fmt:parseNumber integerOnly="true" value="${totalCount/10 }"
						var="totalPage" />
					<c:if test="${(totalCount%10) > 0 }">
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
						<a href="admin_sellerApplyAuth?sub=6&page=${startPage-5 }&fromDate=${param.fromDate }&toDate=${param.toDate }"> ‹ </a>
						</c:if>
					<c:if test="${page < 6 && page > 1}">
						<a href="admin_sellerApplyAuth?sub=6&page=1&fromDate=${param.fromDate }&toDate=${param.toDate }"> ‹ </a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<c:if test="${page eq i }">
							<strong>${i }</strong>
						</c:if>
						<c:if test="${page ne i }">
							<a href="admin_sellerApplyAuth?sub=6&page=${i }&fromDate=${param.fromDate }&toDate=${param.toDate }"><span>${i }</span></a>
						</c:if>
					</c:forEach>
					<c:if test="${startPage+5 < totalPage }">
						<a href="admin_sellerApplyAuth?sub=6&page=${startPage+5 }&fromDate=${param.fromDate }&toDate=${param.toDate }"> › </a>
					</c:if>
					<c:if test="${startPage+5 > totalPage && page < totalPage}">
						<a href="admin_sellerApplyAuth?sub=6&page=${totalPage }&fromDate=${param.fromDate }&toDate=${param.toDate }"> › </a>
					</c:if>
				</div>
		</div>
	</div>
			<!-- footer -->
		<footer>
			<%@include file="../common/footer.jsp" %>
		</footer>
</body>
</html>