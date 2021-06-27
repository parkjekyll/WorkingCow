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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<link rel="stylesheet" href="./css/paging.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">
<meta charset="UTF-8">
<title>내 정보</title>
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
.container{width: 100%; height: 100%; margin: 0; padding: 0; min-width: 1903px; min-height: 980px;}

.sidebar {padding-top: 20px;	width: 15%;	height: 100%; margin-left: 40px; position: relative; top: 0; float: left;}
.sidebar ul {text-align: left;	padding: 0;}
.sidebar ul li {text-decoration: none;	padding-bottom: 20px;}
.sidebar ul li a:active,.sidebar ul li a:hover {font-weight: bold;	text-decoration: none;}

.side,.side_on {text-decoration: none; color: black;}
.side_on {font-weight: 900; font-size: 18px;}
.sidebar_list {margin-top: 40px;}

.main {padding-top: 20px; float: right;	position: relative;	width: 82%;	min-height:980px; height: 100%; border-left: 1px solid black;	padding-left : 20px;}
.main ul {display: block;}
.main ul li {border: 1px solid lightgray; margin-left: 10px; text-align: center; float: left;}
.main_menudetail {width: 100%; min-width: 1539px; height: 38px;}
.che {float: left; padding-right: 300px; text-align: center;}
.che_name, .che_data {text-align: center; padding-bottom: 15px; color: white;}

.detail {width: 100%; text-align: center; min-width: 1539px;} 
.detail td,.detail th {padding-right: 40px;}
.detail tr {border-bottom: 1px solid lightgray; height: 60px;}
.detail tr:first-child {border-bottom: 3px solid lightgray;}
.detail th {padding-bottom: 20px;}
.detail img {width: 40px; height: 30px;}
.pay {float: left; width: 60%; margin-top: 40px; border: 2px solid lightgray;}
.main button{position: relative; left: 20px; height: 36px;}
#memberSearch {width: 200px; height: 38px; float: right; border: 1px solid lightgray; border-radius: 7px;}
.detail input[type="text"] {width: 350px; height: 38px; float: right;}

#shipSearch {height: 36px; width: 150px; margin-left: 10px; float: left;}
.paging {margin: 0}
.che:last-child {padding-right: 0}
</style>
<script type="text/javascript">
function changeMyInfoPopup() {
	var url = "./changeMyInfo";
	var name = "chagneMyInfo";
	var option = "width = 800, height = 800, top = 100, left = 200, location= no"
	window.open(url, name, option);
}
$("#datepicker").datepicker({
	 dateFormat: 'yy-mm-dd'
	,showOtherMonths: true
	,showMonthAfterYear:true
	,changeYear: true
	,showOn: "both"
	,buttonImage:"./images/calendar.gif"
	,buttonImageOnly:true
	,buttonText:"선택"
	,yearSuffix:"년"
	,monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12']
	,monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	,dayNamesMin:['일','월','화','수','목','금','토']
	,dayNames:['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
});
$('#datepicker').datepicker('setDate', 'today')
$(function () {
	$.datepicker.setDefaults({
		 dateFormat: 'yy-mm-dd'
		,showOtherMonths: true
		,showMonthAfterYear:true
		,changeYear: true
		,showOn: "both"
		,buttonImage:"./images/calendar.gif"
		,buttonImageOnly:true
		,buttonText:"선택"
		,yearSuffix:"년"
		,monthNamesShort:['1','2','3','4','5','6','7','8','9','10','11','12']
		,monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
		,dayNamesMin:['일','월','화','수','목','금','토']
		,dayNames:['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
	});
	//input을 datepicker로 선언
	$("#fromDate").datepicker();
	$("#toDate").datepicker();
	
	//from의 초기값 오늘날짜로 설정
	$('#fromDate').datepicker('setDate', 'today');		
	//To의 초기값 다음날일로 설정
	$('#toDate').datepicker('setDate', '+1D');
	//종료일을 시작일 이후로 선택하게 설정
	$('#fromDate').datepicker();
    $('#fromDate').datepicker("option", "maxDate", $("#toDate").val());
    $('#fromDate').datepicker("option", "onClose", function ( selectedDate ) {
     $("#toDate").datepicker( "option", "minDate", selectedDate );
    });

    $('#toDate').datepicker();
    $('#toDate').datepicker("option", "minDate", $("#fromDate").val());
    $('#toDate').datepicker("option", "onClose", function ( selectedDate ) {
        $("#fromDate").datepicker( "option", "maxDate", selectedDate );
    });
});
</script>
</head>
<body>
	<!-- navbar -->
	<%@ include file="../common/navbar.jsp" %>
	<div class="header">
		<div class="head" style="color: white; font-weight: bold;">My page</div>
	
		<!-- 회원 정보, 마일리지, 쿠폰, 후기-->
		<%@ include file="./myPage_account.jsp" %>
	</div>
	<!-- 사이드바를 이용해 목록 이동 가능 -->
	<div class="container">
		<%@ include file="myPage_sidebar.jsp" %>
	
		<!-- 사이드바로 이동한 목록 출력 -->
		<div class="main">
			<h3>캐시 충전 내역</h3>
			<hr>
			<!-- 날짜로 검색 -->
			<form name="searchFrm" action="myPage_chargeCashList?sub=6" method="get">
				<input type="text" id="fromDate" name="fromDate" > &nbsp;&nbsp;
				<input type="text" id="toDate" name="toDate" >
				<button type="submit" class ="btn btn-primary"  id="btnSearch">조회</button>
				<button type="button" class ="btn btn-primary" onclick="location.href='./myPage_chargeCashList?sub=6'">초기화</button>
			</form>
			<hr>
	
	
			<div class="main_detail">
				<table class="detail">
					<tr>
						<th style="width: 20%;">충전날짜</th>
						<th style="width: 20%;">충전 요청금액</th>
						<th style="width: 20%;">현재 잔액</th>
						<th style="width: 20%;">충전수단</th>
						<th style="width: 20%;">상태</th>
					</tr>
					<c:forEach items="${data }" var="data">
					<tr>
						<td style="width: 20%;"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${data.cashVO.cash_chargeDate }"/></td>
						<td style="width: 20%;"><fmt:formatNumber value="${data.cashVO.cash_charge }"/> WC</td>
						<td style="width: 20%;"><fmt:formatNumber value="${data.cashVO.cash_amount }" /> WC</td>
						
						<td style="width: 20%;">무통장입금</td>			
						
						<c:if test="${data.cashVO.cash_status eq 2}"> <td style="width: 20%;">입금확인중</td> </c:if>
						<c:if test="${data.cashVO.cash_status eq 3}"> <td style="width: 20%;">승인</td> </c:if>
						<c:if test="${data.cashVO.cash_status eq 4}"> <td style="width: 20%;">지급완료</td> </c:if>
						<c:if test="${data.cashVO.cash_status eq 0}"> <td style="width: 20%;">최초상태</td> </c:if>
						
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
						<a href="myPage_chargeCashList?sub=6&page=${startPage-5 }&fromDate=${param.fromDate }&toDate=${param.toDate }"> ‹ </a>
						</c:if>
					<c:if test="${page < 6 && page > 1}">
						<a href="myPage_chargeCashList?sub=6&page=1&fromDate=${param.fromDate }&toDate=${param.toDate }"> ‹ </a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<c:if test="${page eq i }">
							<strong>${i }</strong>
						</c:if>
						<c:if test="${page ne i }">
							<a href="myPage_chargeCashList?sub=6&page=${i }&fromDate=${param.fromDate }&toDate=${param.toDate }"><span>${i }</span></a>
						</c:if>
					</c:forEach>
					<c:if test="${startPage+5 < totalPage }">
						<a href="myPage_chargeCashList?sub=6&page=${startPage+5 }&fromDate=${param.fromDate }&toDate=${param.toDate }"> › </a>
					</c:if>
					<c:if test="${startPage+5 > totalPage && page < totalPage}">
						<a href="myPage_chargeCashList?sub=6&page=${totalPage }&fromDate=${param.fromDate }&toDate=${param.toDate }"> › </a>
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