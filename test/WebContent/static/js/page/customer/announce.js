$(document).ready(function() {
		$.ajax({
			url : "../../announce/getAnnounce",
	//		data : {
	//			id : ids
	//		},
			dataType : "json",
			success : function(data) {
				if(data.success === 1) {
					var datas = data.object;
					var rows = [];
					for (i in datas) {
						rows.push('<div class="holder">');
						rows.push('<div class="month green">');
						rows.push('<p>' + datas[i].createTime.substring(0, 7) + '</p>');
						rows.push('</div>');
						rows.push('<div class="day">');
						rows.push('<p>' + datas[i].createTime.substring(8, 10) + '</p>');
						rows.push('</div>');
						rows.push('</div>');
						rows.push('<h5>');
						rows.push(datas[i].title);
						rows.push('</h5>');
						
//						rows.push('<h5><span class="dropcap"><strong>28</strong><span>06</span></span>');
//						rows.push(datas[i].title);
//						rows.push('</h5>');
						rows.push('<div class="wrapper pad_bot2"><p class="pad_bot1">');
						rows.push(datas[i].content);
						rows.push('</div>');
					}
					var htmls = rows.join("");
					$('#content').html(htmls);
				}
			}
		});

});