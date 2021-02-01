/**
 *
 */

//投稿を押したとき
('#post').on('click', function() {
	console.log('click');
	var tw = $('#tweet').val();
	$.ajax({
		url: "http://localhost:8080/D20201006_keiji/input_tw",
		type: "POST", // GET,POSTとか
		dataType: "text",
		data: { // 送信するデータ
			tweet: tw
		}
	}).fail(function(data) {
		// 通信失敗時の処理
		console.dir(data);
	}).always(function(data) {
		// 常に実行する処理
		$("#exampleModal").modal('hide'); // モーダルを閉じる
	});
});