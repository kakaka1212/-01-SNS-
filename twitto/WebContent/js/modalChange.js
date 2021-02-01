/**
 *
 */

 alert("test");

$(function(){
	/* 切り替えボタンクリック時 */
	$('#to-modal2').on('click', function(){
		$('Modal1').modal('hide');
		$('Modal2').modal('show');
	});

	$('#to-modal1').on('click', function(){
		 alert("test");
		$('Modal2').modal('hide');
		$('Modal1').modal('show');
	});
});