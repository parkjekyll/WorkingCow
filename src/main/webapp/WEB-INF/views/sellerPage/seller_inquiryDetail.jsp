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

<meta charset="UTF-8">
<title>판매자 페이지</title>
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

.emp {vertical-align: middle; margin-left: 50px; float: left;}
.emp, .empother {margin-top: 40px;}
.empname {padding-left: 30px; padding-top: 80px; vertical-align: middle; padding-right: 100px;width: 25%; float: left;}
.empother{float: left; vertical-align: middle; position: relative; right: -300px;}


.header {
	margin: 0;
	padding: 0;
	min-width: 100%;
	width: 100%;
	height: 250px; 
	background-color: black;
}
.container{
	width: 100%;
	height: 100%; 
	
	margin: 0;
	padding: 0;
	
	min-width: 100%;
	min-height: 1100px;	

}
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
.sidebar_list{margin: 40px;}
.sidebar_list{margin: 40px;}
.main {
	padding-top: 20px;
	float: right;
	
	position: relative;
	width: 82%;
	height: 100%;
	border-left: 1px solid black;
	padding-left : 20px; 
}

ul li {
	list-style: none;
}
.main_detail{
	padding-left: 20px;
}
.main_detail{
	padding-left: 20px;
}
.main ul {
	display: block;
}

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

.che_name, .che_data {
	text-align: center;
	padding-bottom: 15px;
	color: white;
}

td:active{
	border: 2px; solid black;
}

.main button{
	position: relative;
	left: 20px;
}
#title_table, #content_table, #buy_table{
	margin: 5px 10px;
}
.button{
	margin: 0px 20px;
}
</style>
</head>
<body>
	<!-- navbar -->
	<%@ include file="../common/navbar.jsp" %>
	<div class="header">
		<div class="head" style="color: white; font-weight: bold;">Seller Page</div>

		<%@ include file="./seller_account.jsp" %>
	</div>
	<!-- 사이드바를 이용해 목록 이동 가능 -->
	<div class="container">
		<%@ include file="./seller_sidebar.jsp" %>
	
		<!-- 사이드바로 이동한 목록 출력 -->
		<div class="main">
			<h3>문의함 상세보기</h3>
			<hr>
			<table id="title_table">
				<tr>
					<td style="padding: 0px 63px 0px 10px;">제목</td>
					<td style="width: 600px; padding: 0px 0px 0px 20px">${getInquiryContent.inquiryToSellerVO.inquiry_title }</td>
				</tr>
			</table>
				<hr>
			<table id="content_table">
				<tr>
					<td style="padding: 0px 10px;">문의 상품명</td>
					<td style="width: 600px; padding: 0px 0px 0px 20px">${getInquiryContent.productVO.product_title }</td>
					
					<td style="padding: 0px 10px;">등록 날짜</td>
					<td style="width: 500px; padding: 0px 0px 0px 20px">${getInquiryContent.inquiryToSellerVO.inquiry_date }</td>
				</tr>
			</table>
			<hr>
			<table id="buy_table">
				<tr>
					<td style="padding: 0px 46px 0px 10px;">구매자</td>
					<td style="width: 600px; padding: 0px 0px 0px 20px">${getInquiryContent.customerVO.customer_nick }<span style="font-size: 12px"> [구매자]</span></td>
				</tr>
			</table>
			<hr>

			<div class="main_detail">
				${getInquiryContent.inquiryToSellerVO.inquiry_content }
			</div>
			
			<div class="button" style="text-align: center;">
				<a href="${pageContext.request.contextPath }/sellerInquiry.do"><button type="button" class="btn btn-primary" style="margin: 0px 20px;">뒤로가기</button></a>
			<c:if test="${getInquiryContent.inquiryToSellerVO.inquiry_status eq 'N'.charAt(0) }">
				<a href="${pageContext.request.contextPath }/replyInquiryPage.do?inquiry_no=${getInquiryContent.inquiryToSellerVO.inquiry_no}"><button type="button" class="btn btn-primary" style="margin: 0px 20px;">답변하기</button></a>
			</c:if>
			<c:if test="${getInquiryContent.inquiryToSellerVO.inquiry_status eq 'Y'.charAt(0) }">
				<button type="button" class="btn btn-primary" style="margin: 0px 20px;" disabled>답변완료</button>
			</c:if>
			<c:if test="${getInquiryContent.inquiryToSellerVO.inquiry_status eq 'D'.charAt(0) }">
				<button type="button" class="btn btn-primary" style="margin: 0px 20px;" disabled>삭제된 문의입니다.</button></a>
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