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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css">
<meta charset="UTF-8">
<style type="text/css">
#wrapper{
	width: 800px;
 	background-color: #ffffff; 
 	-webkit-border-radius: 5px; 
 	-moz-border-radius: 5px; 
 	border-radius: 5px;    
 	border: 1px solid #e5e5e5;
    margin: 0 auto;
    padding-left: 80px;
    padding-right: 80px;
    box-sizing: border-box;
    padding-top: 50px;
    padding-bottom: 60px;
    height:100%;
 }
#infoSection{height:100%;}
#key_value_section{
 position:relative;
 margin-top:10px;}
#key_section{text-align: center; background-color: }
#key_value_section #key_section {
    position: absolute;
    width: 200px;
    height: 30px;
    line-height: 50px;
    font-size: 16px;
    font-family: NanumGothicBold;
}
#key_value_section #value_section {
    padding-left: 180px;
}
#info_name,#info_price,#info_rate{
    width: 100%;
    box-sizing: border-box;
    height: 50px;
    border-radius: 4px;
    border: 1px solid #e1e1e1;
    font-family: NanumGothic;
    color: #333333;
    font-size: 14px;
    padding-left: 20px;
    padding-right: 20px;
}
#infoCode{
    width: 70%;
    box-sizing: border-box;
    height: 50px;
    border-radius: 4px;
    border: 1px solid #e1e1e1;
    font-family: NanumGothic;
    color: #333333;
    font-size: 14px;
    padding-left: 20px;
    padding-right: 20px;
}
#button{text-align: center;}
#searchCouponCode{margin-left:10px;}
</style>
<script type="text/javascript">
function upload() {
	var form = $("#useCouponIssuance").serialize();
	
	$.ajax({
		type: "POST",
		data: form,
		url : "./ajaxCoupon",
		dataType : "json",
		success:function(data) {
			alert("??????????????? ?????????????????????.");
			$('#info_name').val(data.coupon_name);
			$('#info_rate').val(data.coupon_discountPercent+"%");
			$('#info_price').val(data.coupon_discountPrice+"???");
			$('#info_no').val(data.coupon_no);
		},
		error:function() {
			alert("????????? ????????? ?????????????????????.");
		}
	});
}

function registered() {
	var form = $("#useCouponIssuance").serialize();
	
	$.ajax({
		type: "POST",
		data: form,
		url : "./registeredCoupon",
		success:function(data) {
			alert("????????? ??????????????????.");
			self.close();
			opener.location.reload();
		},
		error:function() {
			alert("????????? ?????? ??????!");
		}
	});
}
</script>
<title>?????? ????????????</title>
</head>
<body>
<div id="wrapper">
			<h2>?????? ??????</h2>
			<hr>
			<form id="useCouponIssuance">
			<div id="infoSection">
				<div id="key_value_section">
					<div id="key_section">?????? ??????</div>
					<div id="value_section"><input type="text" id="infoCode" name="coupon_code"><button type="button" onclick="upload();" class="btn btn-primary" id="searchCouponCode">????????????</button></div>
				</div>								
				<div id="key_value_section">
					<div id="key_section">?????????</div>
					<div id="value_section"><input type="text" id="info_name" name="coupon_name" disabled value="????????? ???">
					<input type="hidden" id="info_no" name="coupon_no" value=""></div>
				</div>
				<div id="key_value_section">
					<div id="key_section">?????? ?????????</div>
					<div id="value_section"><input type="text" id="info_rate" name="coupon_discountRate" disabled value="????????? ???">
					</div>
				</div>
				
				<div id="key_value_section">
					<div id="key_section">?????? ????????????</div>
					<div id="value_section"><input type="text" id="info_price" name="coupon_discountPrice" disabled value="????????? ???">
					</div>
				</div>
				<br>			
				<br>
			</div>
				<div id="button">
				<button type="button" onclick="registered();" class="btn btn-primary">????????????</button>
				<button type="button" class="btn btn-info">????????????</button>
				</div>
			</form>
		</div>

</body>
</html>