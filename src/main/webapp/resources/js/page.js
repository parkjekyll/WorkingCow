$(document).ready(function () {
	$(window).scroll(function () {
		if ($(this).scrollTop() > 345) {
			$('.sidebar').css('position','fixed');
			$('.sidebar').css('top','0');
		} else {
			$('.sidebar').css('position','relative');
			$('.sidebar').css('top','0');
		}
	})
});

function changeMyInfoPopup() {
	var url = "myPage_changeMyInfo";
	var name = "myPage_chagneMyInfo";
	var option = "width = 800, height = 800, top = 100, left = 200, location= no"
	window.open(url, name, option);
}

//이미지버튼으로 이미지 삽입
$(function () {
	$('#itemImage-upload').click(function (e) {
		e.preventDefault();
		$('#product_img').click();
	});
});

       function changeValue(obj){
    	alert(obj.value);
         }
//포트폴리오버튼으로 이미지 삽입
$(function () {
	$('#portfolio-upload').click(function (e) {
		e.preventDefault();
		$('#sellerPortfolio').click();
	});
});

       function changeValue(obj){
    	alert(obj.value);
         }
//이미지 파일 삽입시 미리보기
$(function() {
            $("#product_img").on('change', function(){
                readURL(this);
            });
        });

        function readURL(input) {
            if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                    $('#previewImage').attr('src', e.target.result);
                }

              reader.readAsDataURL(input.files[0]);
            }
        }

function addCoupon() {
	window.open('addCoupon','_blank','left=650,top=250,width=800,height=500,scrollbars=no,resizable=0,status=0,menubar=no,titlebar=no,location=no');
	return false;
}

function useCoupon() {
	window.open('myPage_useCoupon','_blank','left=650,top=250,width=800,height=500,scrollbars=no,resizable=0,status=0,menubar=no,titlebar=no,location=no');
	return false;
}