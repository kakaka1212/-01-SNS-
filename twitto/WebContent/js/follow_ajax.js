/**
 *
 */


$('.follow_btn').on('click', function(){
  var flId =  $(this).data('id');
	//console.log(flId);
  $.ajax({
    url : "http://localhost:8080/D20201006_keiji/delete",
    type : "POST",
    data : {followId : flId},
    success : function() {
		console.log('成功です！');
		 $.get("http://localhost:8080/D20201006_keiji/fdisp" , function(data){
			$("html").html(data);
		});
    },
    error : function() {
      console.log('通信エラーです');
    }
  })
});




