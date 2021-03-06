<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
*{margin: 0; padding: 0}
.navbar_container li {text-decoration: none; float: left;}
.navbar_container ul {list-style: none;}
.navbar_container a {text-decoration: none; color: black; }
.navbar_logo_img,.navbar_logo_img img {width: 50px; height: 50px; cursor: pointer;}
.navbar_logo_img {position: relative; left: 15%; float: left; z-index: 1;}
.navbar_container {font-family:"NotoSansKR-Regular"; width: 100%; min-width: 1903px; height: 80px; border-bottom: 1px solid #f5f5f5; margin-top: 15px;}
.navbar_menu{height: 50px; margin-top: 15px;}
.navbar_maincate {height: 50px;}
.navbar_subcate {height: 30px;}
.navbar_menu {position: absolute; left: 0; top: 0; width: calc(100% - 400px); min-width: 1503px; padding-left: 400px;}
.navbar_maincate {font-size: 20px; padding: 0 30px; line-height: 2.3; }
.navbar_subcate {display: none; position: absolute; top: 50px; left: 0; background-color: #f5f5f5; width: 100%; min-width: 1903px; padding-left: 410px;}
.navbar_maincate:hover > .navbar_subcate {display: block;}
.navbar_subcate_menu {padding-right: 40px; font-size: 15px;}
.navbar_login {display: block; height: 50px; color: #6e6d7a; z-index: 1; position: relative; left: 66%; font-size: 20px; padding-top:10px; width: 330px;}
@font-face {font-family: "NotoSansKR-Regular"; src:url("resources/font/NotoSansKR-Regular.otf") format("truetype"); font-style: normal; font-weight: normal; font-size: 20px;}
#navbarLoginBox{font-size: 14px;}
.navbar_maincate a:hover{color: #505bf0;}
#cate_on>a{color: #505bf0;}
.navbar_subcate_menu>a{color: black;}
.logout {margin-left: 15px;}
</style>
</head>
<body>

	<div class="navbar_container">
		<div class="navbar_logo_img"><img src="./images/logo-white.png" onclick="location.href='./'"></div>
		<ul class="navbar_menu">
			<li class="navbar_maincate" <c:if test="${mainCate_no eq 1}">id="cate_on"</c:if>><a href="board?cate=1" >IT</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=1">??? ????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=2">????????? ???</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=3">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=4">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=5">??????????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=6">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=7">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=1&sub=8">??????</a></li>
					
				</ul>
			</li>
			<li class="navbar_maincate" <c:if test="${mainCate_no eq 2 }">id="cate_on"</c:if>><a href="board?cate=2">??????</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=8">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=10">?????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=11">???????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=12">3D & VR</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=13">?????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=14">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=15">??????????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=16">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=2&sub=17">??????</a></li>
				</ul>
			</li>
			<li class="navbar_maincate" <c:if test="${mainCate_no eq 3 }">id="cate_on"</c:if>><a href="board?cate=3">?????????</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=18">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=19">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=20">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=21">???????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=22">???????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=23">?????????????????? ??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=24">???????????? & ????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=25">?????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=26">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=27">??????&???????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=3&sub=28">??????</a></li>
				</ul>
			</li>
			<li class="navbar_maincate" <c:if test="${mainCate_no eq 4 }">id="cate_on"</c:if>><a href="board?cate=4">?????????</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=29">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=30">???????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=31">?????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=32">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=33">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=34">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=35">?????? ??? ??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=36">???</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=4&sub=37">??????</a></li>
				</ul>
			</li>
			<li class="navbar_maincate" <c:if test="${mainCate_no eq 5 }">id="cate_on"</c:if>><a href="board?cate=5">??????</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="board?cate=5&sub=38">????????? ??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=5&sub=39">?????? ??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=5&sub=40">??? ??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=5&sub=41">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=5&sub=42">??????</a></li>					
				</ul>
			</li>
			<li class="navbar_maincate" <c:if test="${mainCate_no eq 6 }">id="cate_on"</c:if>><a href="board?cate=6">??????</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=43">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=44">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=45">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=46">???????????????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=47">?????? ??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=48">??????</a></li>
					<li class="navbar_subcate_menu"><a href="board?cate=6&sub=49">??????</a></li>
				</ul>
			</li>
			<li class="navbar_maincate" <c:if test="${customerService ne null }">id="cate_on"</c:if>><a href="notice">????????????</a>
				<ul class="navbar_subcate">
					<li class="navbar_subcate_menu"><a href="notice">????????????</a></li>
					<li class="navbar_subcate_menu"><a href="FAQs">FAQs</a></li>
					<li class="navbar_subcate_menu"><a href="inquiryToAdmin">?????? ?????????</a></li>
				</ul>
			</li>
		</ul>
		
		<div class="navbar_login" id="navbarLoginBox">
			
				<c:if test="${empty sessionCustomer && empty sessionAdmin}">
					<a href="${pageContext.request.contextPath }/login" style="float: right;">Login</a>
				</c:if>
				<c:if test="${!empty sessionCustomer }">
					<span id="logInfo" style="float: right;"><a href="${pageContext.request.contextPath }/myPage_orderList?sub=1">${sessionCustomer.customer_nick }</a>??? ???????????????. <a href="${pageContext.request.contextPath }/logoutProcess.do" class="logout">????????????</a></span>
				</c:if>
				<c:if test="${!empty sessionAdmin }">
					<span id="logInfo" style="float: right;"><a href="${pageContext.request.contextPath }/admin_memberManagement?sub=1">${sessionAdmin.admin_name }</a>??? ???????????????. <a href="${pageContext.request.contextPath }/logoutProcess.do" class="logout">????????????</a></span>
				</c:if>
			
		</div>
	</div>
</body>
</html>