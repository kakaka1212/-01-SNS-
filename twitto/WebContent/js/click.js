/**
 *
 */

$(".other_btn").on('click', function(){
	var uId = $(this).data('id');
	$.ajax({
		url : "http://localhost:8080/D20201006_keiji/otherac",
	    type : "POST",
	    data : {other_uId : uId},
	    success : function() {
			console.log('成功です！')
			window.location.href="http://localhost:8080/D20201006_keiji/ac_other.jsp";
	    },
	    error : function() {
	      console.log('通信エラーです')
	    }
	})
})

var firstClick = true;
$('i.fas.fa-heart.fa-lg').on('click', function(){
	var id = $(this).data('id');
	var uid = document.querySelector('.u'+id).textContent;
	console.log(uid);
	var text = document.querySelector('.t'+id).textContent;
	console.log(text);
	var date = document.querySelector('.d'+id).textContent;
	console.log(date);
	if(firstClick){
		document.querySelector(".color"+id).style.color = 'red';
		firstClick = false;
		$.ajax({
			url : "http://localhost:8080/D20201006_keiji/like",
		    type : "POST",
		    data : {
				userId: uid,
				tweet: text,
				tmStamp: date
			},
		    success : function() {
				console.log('成功です！');
			},
		    error : function() {
		      console.log('通信エラーです');
			}
		})
	}else{
		document.querySelector(".color"+id).style.color = '#5989cf';
		firstClick = true;
		$.ajax({
			url : "http://localhost:8080/D20201006_keiji/likedelete",
		    type : "POST",
		    data : {
				userId: uid,
				tweet: text,
				tmStamp: date
			},
		    success : function() {
				console.log('成功です！');
			},
		    error : function() {
		      console.log('通信エラーです');
			}
		})
	}
});