/*
 * @author : LiJingJing
 * @time    : 2012-11-10
 * @自定义SELECT插件
 */
 // 创建一个闭包    
(function($) {
	
	$("<link>").attr({ 
		rel: "stylesheet",
		type: "text/css",
		href: baseUrl + "/static/css/select/select.css"
	}).appendTo("head");
	
	// 插件的定义    
	$.fn.JJ_select = function(options) {   
	
	//替换用户自定义参数
	var opts = $.extend({}, $.fn.JJ_select.defaults, options);

	//遍历所有
	return this.each(function() {   
		var $this = $(this).find(".jj-select-box");
		
		//设置input，一个SELECT对应一个固定的input
		$this.append('<input name="'+opts.input_id+'" type="hidden" value="" id="'+opts.input_id+'"/>');
		//获取input元素
		var inputselect = $this.find("#"+opts.input_id);
		//SELECT框右边的打开关闭状态图标
		$this.prepend("<em class='jj-select-icon1'></em>");
		
		/*$this.find(".content-box,ul.list2").click(function(event){
			return false;	
		});*/
		
		//点击删除选择项目
		$this.find("cite").unbind(".close").on("click.close",".sel-close",function(){
			//获取删除ID
			var _id = $(this).parent().attr("rel");
			//移除父节点元素
			$(this).parent().remove();
			//移除选中状态
			$this.find(".item-"+_id).removeClass("active active1");
			//设置改变input值
			var _arr = inputselect.val().split(',');
			var _arr1 = $.grep(_arr,function(n,i){
				return n != _id;
			});
			inputselect.val(_arr1.join(','));
			//如果没有选中任何，则显示“请选择XXX”
			$this.find("cite").text() == '' && $this.find("cite").text($this.find("cite").attr("title"));
			return false;
		});
		
		//风格一
		if(opts.style == 1){
			
			//SELECT下拉框是否浮动
			if(opts.isAbsolute){
				$this.find("ul.list2").css({"position":"absolute","left":0,"right":0});	
			}
			
			//点击SELECT文本框
			//$this.find(opts.select_text).click(function(event){
			$this.off(".a",opts.select_text).on("click.a",opts.select_text,function(event){
				var ul = $this.find("ul.list2");
				if(ul.is(":hidden")){
					//ul.slideDown("fast");
					ul.show();
					$this.find("em.jj-select-icon1").removeClass("jj-select-icon1").addClass("jj-select-icon2");
				}else{
					ul.hide();
					$this.find("em.jj-select-icon2").removeClass("jj-select-icon2").addClass("jj-select-icon1");
					//ul.slideUp("fast");
				}
				return false;
			});
			
			//点击选中事件
			$this.off(".b","ul.list2 li a").on("click.b","ul.list2 li a",function(event){
				//获取选中元素的文本
				var txt = $(this).attr("title") || $(this).text();
				//获取选中元素的值
				var value = $(this).attr(opts.options_value);
				
				var _arr = inputselect.val().split(',');
				//c_num=1为单选
				if(opts.c_num == 1){
					opts.isShowValue && $this.find(opts.select_text).html(txt);
					$this.find("em.jj-select-icon2").removeClass("jj-select-icon2").addClass("jj-select-icon1");
					inputselect.val(value);
				}else{
					//多选
					if(inputselect.val() == ''){
						$this.find(opts.select_text).html("<span rel='"+value+"' class='sel-items sel-items-"+value+"'>"+txt+"<i class='sel-close jj-select-icon3'>x</i></span>");
						inputselect.val(value);
					}else{
						if($.inArray(value,_arr) == -1){
							$this.find(opts.select_text).append("<span rel='"+value+"' class='sel-items sel-items-"+value+"'>"+txt+"<i class='sel-close jj-select-icon3'>x</i></span>");
							inputselect.val(inputselect.val() + ","+value);
						}else{
							var _arr1 = $.grep(_arr,function(n,i){
								return n != value;
							});
							inputselect.val(_arr1.join(','));
							$this.find(".sel-items-"+value).remove();
						}
					}
				}
				if(opts.c_num == 1){
					$this.find("ul.list2 li a").removeClass("active1");
					$(this).addClass("active1");
				}else{
					$(this).toggleClass("active1");	
				}
				
				opts.isHideOption && $this.find("ul").hide();
				$this.find("em.jj-select-icon2").removeClass("jj-select-icon2").addClass("jj-select-icon1");
			});
			
			
			
		}else if(opts.style == 2){
			
			$this.off(".a",opts.select_text).on("click.a",opts.select_text,function(event){
				var ul = $this.find(".content-box");
				if(ul.is(":hidden")){
					ul.show();
					$this.find("em.jj-select-icon1").removeClass("jj-select-icon1").addClass("jj-select-icon2");
				}else{
					ul.hide();
					$this.find("em.jj-select-icon2").removeClass("jj-select-icon2").addClass("jj-select-icon1");
				}
				return false;
			});
			
			$this.off(".b",".content-box .list1 li").on("click.b",".content-box .list1 li",function(){
				var _index = $this.find(".content-box .list1 li").index($(this)[0]);
				$(this).siblings().removeClass("active");
				$(this).addClass("active");	
				$(this).parent().siblings("dl.list1-content").hide();
				$(this).parent().siblings("dl.list1-content").eq(_index).show();
			});
			
			$this.off(".c",".content-box .list1-content dd a").on("click.c",".content-box .list1-content dd a",function(){
				var txt = $(this).attr("title") || $(this).text();
				var value = $(this).attr(opts.options_value);
				var _arr = inputselect.val().split(',');
				if(opts.c_num == 1){
					opts.isShowValue && $this.find(opts.select_text).html(txt);
					$this.find("em.jj-select-icon2").removeClass("jj-select-icon2").addClass("jj-select-icon1");
					inputselect.val(value);
				}else{
					if(inputselect.val() == ''){
						$this.find(opts.select_text).html("<span rel='"+value+"' class='sel-items sel-items-"+value+"'>"+txt+"<i class='sel-close jj-select-icon3'>x</i></span>");
						inputselect.val(value);
					}else{
						
						if($.inArray(value,_arr) == -1){
							$this.find(opts.select_text).append("<span rel='"+value+"' class='sel-items sel-items-"+value+"'>"+txt+"<i class='sel-close jj-select-icon3'>x</i></span>");
							inputselect.val(inputselect.val() + ","+value);
						}else{
							var _arr1 = $.grep(_arr,function(n,i){
								return n != value;
							});
							inputselect.val(_arr1.join(','));
							$this.find(".sel-items-"+value).remove();
						}
					}
				}
				
				//opts.c_num == 1 && opts.isHideOption && $this.find(".content-box").hide();
				opts.isHideOption && $this.find(".content-box").hide();
				if(opts.c_num == 1){
					$this.find(".content-box .list1-content dd a").removeClass("active1");
					$(this).addClass("active1");
				}else{
					$(this).toggleClass("active1");	
				}
				
			});
			
		}
		
		var _fun1 = function(){
			if(opts.style == 1){
				$this.find("ul.list2").hide();
			} else if(opts.style == 2){
				$this.find(".content-box").hide();
			}
			//改变打开关闭SELECT状态
			$this.find("em.jj-select-icon2").removeClass("jj-select-icon2").addClass("jj-select-icon1");
		}
		//是否点击SELECT以外的区域就隐藏SELECT
		if(opts.isDOC){
			//$("#data-iframe").size() && $(window.frames["data-iframe"].document).find($this).size() && $(window.frames["data-iframe"].document).off(".a",$this).on("click.a",$this,_fun1);
			$(document).off(".a",$this).on("click.a",$this,_fun1);
			//$(window.parent.document).off(".a",$this).on("click.a",$this,_fun1);
		}
		
	});
  };
  
  // 定义暴露format函数    
  $.fn.JJ_select.format = function(txt) {    
    return '<strong>' + txt + '</strong>';    
  };
  
  // 插件的defaults    
  $.fn.JJ_select.defaults = {
	//风格
	style : 1,
	//1单选 or 2多选
	c_num : 1,
	//SELECT显示选择文本
	select_text:'cite',
	//隐藏字段值
    input_id: 'jj-input',
	//相当于options的VALUE属性
	options_value:'selectid' ,
	//是否点击子菜单就隐藏下拉框
	isHideOption : true,
	//是否要显示当前值
	isShowValue : true,
	//是否点击SELECT外部就隐藏SELECT
	isDOC : true,
	//下拉框是否悬浮
	isAbsolute : true
  };    
// 闭包结束    
})(jQuery); 

/*
 * Demo
 ***********************************************************************
 HTML:
 <div id="jj-select-box" class="jj-select-box">
      <cite>请选择分类</cite>
      <ul>
         <li><a href="javascript:;" selectid="1">导航菜单1</a></li>
         <li><a href="javascript:;" selectid="2">导航菜单2</a></li>
         <li><a href="javascript:;" selectid="3">导航菜单3</a></li>
         <li><a href="javascript:;" selectid="4">导航菜单4</a></li>
         <li><a href="javascript:;" selectid="5">导航菜单5</a></li>
      </ul>
  </div>
  <input name="" type="hidden" value="" id="jj-input"/>
  
  JS:
  $(function(){
	    $("#jj-select-box").JJ_select({
			input_id: '#jj-input'
		});
	});

*/

/*
<div id="gc-select-box" class="jj-select-box">
	<em class="jj-select-icon1"></em>
	<cite>(5)班</cite>
	<div class="content-box" style="display: block;">
		<ul class="list1">
			<li class="active"><a class="item" href="#" onclick="return false" selectid="1" >初一<em class="icon1 icon1-5 f-dn"></em></a></li>
			<li><a class="item" href="#" onclick="return false" selectid="2" >初二<em class="icon1 icon1-5 f-dn"></em></a></li>	
			<li><a class="item" href="#" onclick="return false" selectid="3" >初三<em class="icon1 icon1-5 f-dn"></em></a></li>
			<li><a class="item" href="#" onclick="return false" selectid="4" >高一<em class="icon1 icon1-5 f-dn"></em></a></li>
			<li><a class="item" href="#" onclick="return false" selectid="5" >高二<em class="icon1 icon1-5 f-dn"></em></a></li>
			<li><a class="item" href="#" onclick="return false" selectid="6" >高三<em class="icon1 icon1-5 f-dn"></em></a></li>		
		</ul>	
		<dl class="list1-content f-cb" style="display: block;">
			<dd><a class="stu-select-item" id="stu-select-item-1" href="#" onclick="return false"  selectid="1">(1)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-3" href="#" onclick="return false"  selectid="3">(2)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-4" href="#" onclick="return false"  selectid="4">(3)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-5" href="#" onclick="return false"  selectid="5">(4)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item active1" id="stu-select-item-6" href="#" onclick="return false"  selectid="6">(5)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-7" href="#" onclick="return false"  selectid="7">(6)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-8" href="#" onclick="return false"  selectid="8">(7)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-9" href="#" onclick="return false"  selectid="9">(8)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-10" href="#" onclick="return false"  selectid="10">(9)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-11" href="#" onclick="return false"  selectid="11">(10)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-12" href="#" onclick="return false"  selectid="12">(11)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-13" href="#" onclick="return false"  selectid="13">(12)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-14" href="#" onclick="return false"  selectid="14">(13)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-15" href="#" onclick="return false"  selectid="15">(2121)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-16" href="#" onclick="return false"  selectid="16">(11)班<em class="icon1 icon1-5 f-dn"></em></a></dd>
			<dd><a class="stu-select-item" id="stu-select-item-18" href="#" onclick="return false"  selectid="18">(33)班<em class="icon1 icon1-5 f-dn"></em></a></dd>	
		</dl>
		<dl class="list1-content f-cb" style="display: none;">
			<dd><a class="stu-select-item" id="stu-select-item-2" href="#" onclick="return false"  selectid="2">(1)班<em class="icon1 icon1-5 f-dn"></em></a></dd>	
		</dl>
		<dl class="list1-content f-cb" style="display: none;">		<dd><a class="stu-select-item" id="stu-select-item-17" href="#" onclick="return false"  selectid="17">(2112)班<em class="icon1 icon1-5 f-dn"></em></a></dd>	</dl>	<dl class="list1-content f-cb" style="display: none;">	</dl>	<dl class="list1-content f-cb" style="display: none;">	</dl>	<dl class="list1-content f-cb" style="display: none;">	</dl>	</div><input name="clazzId" type="hidden" value="1,6" id="clazzId"></div>
*/