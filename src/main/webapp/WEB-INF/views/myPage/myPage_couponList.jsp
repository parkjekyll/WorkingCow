<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>My page</title>
<style>
*{
	margin: 0;
	padding: 0;
}
head{
	margin-left: 20px;
}
html, body {
	margin: 0;
	padding: 0;
}

.emp {
	vertical-align: middle;
	margin-left: 50px;
	float: left;
}
.emp, .empother {
	margin-top: 40px;
}

.empname {
	padding-left: 30px;
	padding-top: 80px;
	vertical-align: middle;
	padding-right: 100px;
	width: 25%;
	float: left;
}

.empother{
	float: left;
	vertical-align: middle;
    position: relative;
    right: -300px;
}


.header {margin: 0; padding: 0;	min-width: 1903px; width: 100%; height: 250px; background-color: black;}
.container{width: 100%; height: 100%; margin: 0; padding: 0; min-width: 1903px; min-height: 1100px;}

.sidebar {
	padding-top: 20px;
	width: 15%;
	height: 100%;

	margin-left: 40px;
	position: relative;
	top: 0;
	float: left;
	
}

.sidebar ul {
	text-align: left;
	padding: 0;
}

.sidebar ul li {
	text-decoration: none;
	padding-bottom: 20px;
}

.sidebar ul li a:active,
.sidebar ul li a:hover {
	font-weight: bold;
	text-decoration: none;
}

.side,.side_on {text-decoration: none; color: black;}
.side_on {font-weight: 900; font-size: 18px;}
.sidebar_list {margin-top: 40px;}

.button{
float:right;
}

.main {padding-top: 20px; float: right;	position: relative;	width: 82%;	min-height:1100px; height: 100%; border-left: 1px solid black;	padding-left : 20px;}

ul li {
	list-style: none;
}

.main ul {
	display: block;
}
#btnSearch {float: right; margin-top: 30px;}
.main ul li {
	border: 1px solid lightgray;
	margin-left: 10px;
	text-align: center;
	float: left;
}

.che {
	float: left;
	padding-right: 300px;
	text-align: center;
}

.che_alt, .che_data,.che_name {
	text-align: center;
	padding-bottom: 15px;
	color: white;
}

.detail {width: 100%; text-align: center; min-width: 1539px;} 
.detail td,.detail th {padding-right: 40px;}
.detail tr {border-bottom: 1px solid lightgray; height: 60px;}
.detail th {border-bottom: 3px solid lightgray;}
td:active{border: 2px; solid black;}
.pay {float: left; width: 60%; margin-top: 40px; border: 2px solid lightgray;}
.paging {margin: 0}
.che:last-child {padding-right: 0}
</style>
<script type="text/javascript">
$(document).ready(function () {
	$(window).scroll(function () {
		if ($(this).scrollTop() > 400) {
			$('.sidebar').css('position','fixed');
			$('.sidebar').css('top','0');
		} else {
			$('.sidebar').css('position','relative');
			$('.sidebar').css('top','0');
		}
	})
})


</script>
</head>
<body>
	<!-- navbar -->
	<%@ include file="../common/navbar.jsp" %>
	<div class="header">
		<div class="head" style="color: white; font-weight: bold;">My Page</div>

		<!-- 회원 정보, 마일리지, 쿠폰, 후기-->
		<%@ include file="./myPage_account.jsp" %>
	</div>

	<div class="container">
		<%@ include file="myPage_sidebar.jsp" %>
	
		<!-- 사이드바로 이동한 목록 출력 -->
		<div class="main">
			<h3>쿠폰함</h3>
			
			<hr>
			<div class="main_detail">
				<table  class="detail">
					<tr>
						<th style="width: 10%;">번호</th>
						<th style="width: 30%;">쿠폰 명</th>
						<th style="width: 20%;">할인금액</th>
						<th style="width: 20%;">쿠폰 등록 날짜</th>
						<th style="width: 20%;">쿠폰 만기 날짜</th>
					</tr>
					<c:choose>
						<c:when test="${!empty myCoupon }">
						<c:forEach items="${myCoupon }" var="list">
						<tr>
							<td style="width: 10%;">${list.myCoupon_no }</td>
							<td style="width: 30%;">${list.coupon_name }</td>
							<td style="width: 20%;">
								<c:if test="${list.coupon_discountPrice ne 0}"><fmt:formatNumber value="${list.coupon_discountPrice }"/> 원</c:if>
								<c:if test="${list.coupon_discountPercent ne 0}"><fmt:formatNumber value="${list.coupon_discountPercent }"/>%</c:if>
							</td>
							<td style="width: 20%;"><fmt:formatDate pattern="yyyy-MM-dd" value="${list.coupon_date }"/></td>
							<td style="width: 20%;">${list.coupon_expireDate }</td>
						</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
						<tr>
						<td colspan="5"><h2>쿠폰이 없습니다.</h2></td>
						</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<div class="paging">
					<!-- 전체페이지 -->
					<fmt:parseNumber integerOnly="true" value="${totalCount/10 }" var="totalPage" />
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
						<a href="myPage_couponList?sub=4&page=${startPage-5 }&fromDate=${param.fromDate }&toDate=${param.toDate }"> ‹ </a>
						</c:if>
					<c:if test="${page < 6 && page > 1}">
						<a href="myPage_couponList?sub=4&page=1&fromDate=${param.fromDate }&toDate=${param.toDate }"> ‹ </a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<c:if test="${page eq i }">
							<strong>${i }</strong>
						</c:if>
						<c:if test="${page ne i }">
							<a href="myPage_couponList?sub=4&page=${i }&fromDate=${param.fromDate }&toDate=${param.toDate }"><span>${i }</span></a>
						</c:if>
					</c:forEach>
					<c:if test="${startPage+4 < totalPage }">
						<a href="myPage_couponList?sub=4&page=${startPage+5 }&fromDate=${param.fromDate }&toDate=${param.toDate }"> › </a>
					</c:if>
					<c:if test="${startPage+5 > totalPage && page < totalPage}">
						<a href="myPage_couponList?sub=4&page=${totalPage }&fromDate=${param.fromDate }&toDate=${param.toDate }"> › </a>
					</c:if>
				</div>
				<button type="button" class ="btn btn-primary"  id="btnSearch" onclick="useCoupon()">쿠폰등록</button>
		</div>
	</div>
			<!-- footer -->
		<footer>
			<%@include file="../common/footer.jsp" %>
		</footer>
</body>
</html>