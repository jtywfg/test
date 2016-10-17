<%@ page contentType="text/html;charset=UTF-8" %>

<!-- footer S -->
<div id="footer" class="f-cb">
	<p class="f-fl">网络阅卷系统将于12月25号进行更新操纵，请用户及时更新客户端！</p>
    <p class="f-fr">北京望远教育科技股份有限公司 京ICP备14016488号</p>
</div>
<!-- footer E -->

<!--<script type="text/javascript" src="<%=basePath%>/static/js/main.js?v=<%=version%>"></script>-->
<script>
//左边导航点击事件
$("#leftMenu a.toTabs").click(function()
{
	var index = $("#leftMenu a.toTabs").index($(this)[0]);
	var url =$(this).attr("to");
	var thisTitle = $(this).text();
	adminDo.openNewTab(thisTitle,url);
});
/*
 *  加载一级菜单
 */
function loadMenu(callback){
	var _this = this , _html = [];
	$.getJSON(CONFIG._baseUrlName+"data/menu1.json",function(jsonData){
		//tcd.fnPlug.loadMenu(function(jsonData){
			$.each(jsonData.object,function(i,items){
				_html.push('<li>'+'<a href="" id="" style="padding">'+ '<img src="'+items.iconCls+'">'+ '<span>'+items.menuCName+'</span>' +'</li>'+'</a>');
			});
		//});
			$("#nav").html(_html.join(''));
	});
}
</script>



