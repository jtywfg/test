// JavaScript Document

//dg参数字符串
var json_str = json_str || {};
json_str = {
	pageInfo : { page:1,rows:20 }		//分页参数，默认第一页，20个
	,search :  { }
	,sort : { }
};

var adminDo = adminDo || {};
adminDo = {
	webUrl : 'http://192.168.20.46:8080',
	datagrid_Obj : "#dg",	//显示数据对象
	index : '',
	msgBoxTitle : '金太阳提示您',
	//初始化
	init : function()
	{
		
	},
	SetCwinHeight : function(iframeObj){
	  if (document.getElementById){
	   if (iframeObj && !window.opera){
		if (iframeObj.contentDocument && iframeObj.contentDocument.body.offsetHeight){
		 iframeObj.height = iframeObj.contentDocument.body.offsetHeight;
		}else if(document.frames[iframeObj.name].document && document.frames[iframeObj.name].document.body.scrollHeight){
		 iframeObj.height = document.frames[iframeObj.name].document.body.scrollHeight;
		}
	   }
	  }
	},
	reloadTabs : function()
	{
		var tab = window.top.$('#mainContent_tabs').tabs('getSelected');
		var url = $(tab.panel('options').content).attr('src');
		window.top.$("#mainContent_tabs").tabs('update', {
			tab: tab,
options: {
				content:'<iframe name="tabs_x" class="tabs_x" allowtransparency="true" src="'+ url +'" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="auto" style="width: 100%; height: 100%;"></iframe>'
			}
		}); 
		tab.panel('refresh');
	},
	reloadTabs2 : function(name)
	{
		window.top.$("#mainContent_tabs").tabs("select",name);
		var tab = window.top.$('#mainContent_tabs').tabs('getSelected');
		var url = $(tab.panel('options').content).attr('src');
		window.top.$("#mainContent_tabs").tabs('update', {
			tab: tab,
options: {
				content:'<iframe name="tabs_x" class="tabs_x" allowtransparency="true" src="'+ url +'" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="auto" style="width: 100%; height: 100%;"></iframe>'
			}
		}); 
		tab.panel('refresh');
	},
	//打开新窗口
	openNewWindow : function(title,url)
	{
		/*
		onSelect:function(title){ 
  var p = $(this).tabs('getTab', title); 
  p.find('iframe').attr('src','...'); 
} 
		*/
		window.top.$('#myWindow').window({  
			title : title,
			width : 800,   
			height : 500,
			top : 100, 
			maximized : true,	//初始窗口最大化
            left : ($(window).width() - 500) * 0.5
			//  onload="adminDo.SetCwinHeight(this)"
		}).html('<iframe name="tabs_w" class="tabs_w" allowtransparency="true" src="'+ url +'" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="auto" width="100%" height="100%" style="overflow-x:hidden;"></iframe>').window('open');
	},
	//打开添加新Tabs
	openNewTab : function(name,url)
	{
		if(window.top.$("#mainContent_tabs").tabs("exists",name))
		{
			window.top.$("#mainContent_tabs").tabs("select",name);
			adminDo.reloadTabs();
		}
		else
		{
			window.top.$("#mainContent_tabs").tabs('add',{  
				title:name,  
				//href:url,
				content:'<iframe name="tabs_x" class="tabs_x" allowtransparency="true" src="'+ url +'" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" scrolling="auto" style="width: 100%; height: 100%;"></iframe>',  
				closable:true, 
				fit:true, 
				cache:false,
				tools:[{  
					iconCls:'icon-mini-refresh',  
					handler:function(){  
						//刷新TAB
						adminDo.reloadTabs();
					}  
				}]  
			}); 	
		}	
	},
	//创建
	newWork : function(title,url)
	{
		adminDo.openNewTab(title,url);
	},
	//编辑操作
	doEdit : function(url,doc,title)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelected');		
	},
	//编辑
	editWork : function(url,doc,title)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelected');
		if (row){
			url = url + '?id=' + row.id;
			adminDo.openNewWindow(title,url);
		}
		else
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要编辑的数据行！');
		}
	},
	//编辑手机版本
	editWork_phone : function(url,doc,title)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelected');
		if (row){
			url = url + '?_id=' + row._id;
			adminDo.openNewTab(title,url);
		}
		else

		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要编辑的数据行！');
		}
	},
	//删除指定微教 id或id字符串 : 1,2,3,4,5
	destroyWord : function(url,doc)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelections');
		//判断是否选择行
		if (!row || row.length == 0)
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要删除的数据行！','error');	
		}
		else
		{
			$.messager.confirm(adminDo.msgBoxTitle,'你确定删除这行数据吗？',function(r){
				if (r){
					var parms;	
					//循环给提交删除参数赋值	
					$.each(row, function (i, n) 
					{
						if (i == 0) 
						{
							parms = n.id;
						} 
						else 
						{
							parms += "," + n.id;
						}
					});					
					$.post(url,{ ids : parms},function(result){
						if (result.status == 0){
							$(doc).find(adminDo.datagrid_Obj).datagrid('clearSelections');
							$(doc).find(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
						}
						$.messager.show({    // show error message
							title: adminDo.msgBoxTitle,
							msg: result.message
						});
					},'json');
				}
			});
		}
	},
	//删除好友关系 id或id字符串 : 1,2,3,4,5
	destroyWord_friend : function(url,doc)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelections');
		//判断是否选择行
		if (!row || row.length == 0)
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要删除的数据行！','error');	
		}
		else
		{
			$.messager.confirm(adminDo.msgBoxTitle,'你确定删除这行数据吗？',function(r){
				if (r){
					var parms;	
					//循环给提交删除参数赋值	
					$.each(row, function (i, n) 
					{
						if (i == 0) 
						{
							parms = n.friendUser.id;
						} 
						else 
						{
							parms += "," + n.friendUser.id;
						}
					});					
					$.post(url,{ ids : parms},function(result){
						if (result.status == 0){
							$(doc).find(adminDo.datagrid_Obj).datagrid('clearSelections');
							$(doc).find(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
						}
						$.messager.show({    // show error message
							title: adminDo.msgBoxTitle,
							msg: result.message
						});
					},'json');
				}
			});
		}
	},
	//删除指定id或id字符串 : 1,2,3,4,5
	destroyWord_quanzi : function(url,doc)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelections');
		//判断是否选择行
		if (!row || row.length == 0)
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要删除的数据行！','error');	
		}
		else
		{
			$.messager.confirm(adminDo.msgBoxTitle,'你确定删除这行数据吗？',function(r){
				if (r){
					var parms;	
					//循环给提交删除参数赋值	
					$.each(row, function (i, n) 
					{
						if (i == 0) 
						{
							parms = n._id;
						} 
						else 
						{
							parms += "," + n._id;
						}
					});					
					$.post(url,{ ids : parms},function(result){
						if (result.status == 0){
							$(doc).find(adminDo.datagrid_Obj).datagrid('clearSelections');
							$(doc).find(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
						}
						$.messager.show({    // show error message
							title: adminDo.msgBoxTitle,
							msg: result.message
						});
					},'json');
				}
			});
		}
	},
	//删除指定id或id字符串 : 1,2,3,4,5
	destroyWord_tie : function(url,flag)
	{
		var row = $(adminDo.datagrid_Obj).datagrid('getSelections');
		//判断是否选择行
		if (!row || row.length == 0)
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要操作的数据行！','error');	
		}
		else
		{
			$.messager.confirm(adminDo.msgBoxTitle,'你确定操作这些数据吗？',function(r){
				if (r){
					var parms;	
					//循环给提交删除参数赋值	
					$.each(row, function (i, n) 
					{
						if (i == 0) 
						{
							parms = n.id;
						} 
						else 
						{
							parms += "," + n.id;
						}
					});
					adminDo.toPingBi(url,parms,flag);
				}
			});
		}
	},
	//删除指定id或id字符串 : 1,2,3,4,5
	destroyWord_huifu : function(url,flag)
	{
		var row = $(adminDo.datagrid_Obj).datagrid('getSelections');
		//判断是否选择行
		if (!row || row.length == 0)
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要操作的数据行！','error');	
		}
		else
		{
			$.messager.confirm(adminDo.msgBoxTitle,'你确定操作这些数据吗？',function(r){
				if (r){
					var parms;	
					//循环给提交删除参数赋值	
					$.each(row, function (i, n) 
					{
						if (i == 0) 
						{
							parms = n._id;
						} 
						else 
						{
							parms += "," + n._id;
						}
					});
					adminDo.toPingBi(url,parms,flag);
				}
			});
		}
	},
	//删除指定手机版本 _id或_id字符串 : 1,2,3,4,5
	destroyWord_phone : function(url,doc)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelected');
		if (row){
			$.messager.confirm(adminDo.msgBoxTitle,'你确定删除这行数据吗？',function(r){
				if (r){
					$.post(url,{ids:row._id},function(result){
						if (result.status == 0){
							$(doc).find(adminDo.datagrid_Obj).datagrid('clearSelections');
							$(doc).find(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
						} 
						$.messager.show({    // show error message
							title: adminDo.msgBoxTitle,
							msg: result.message
						});
					},'json');
				}
			});
		}
		else
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要删除的数据行！');	
		}
	},
    //删除指定id或id字符串 : 1置顶2精华3火
    destroyWord_tieTab : function(url,type)
    {
        var row = $(adminDo.datagrid_Obj).datagrid('getSelections');
        //判断是否选择行
        if (!row || row.length == 0)
        {
            $.messager.alert(adminDo.msgBoxTitle,'请选择您要操作的数据行！','error');
        }
        else
        {
            $.messager.confirm(adminDo.msgBoxTitle,'你确定操作这些数据吗？',function(r){
                if (r){
                    var parms;
                    //循环给提交删除参数赋值
                    $.each(row, function (i, n)
                    {
                        if (i == 0)
                        {
                            parms = n.id;
                        }
                        else
                        {
                            parms += "," + n.id;
                        }
                    });
                    adminDo.cancelTieTab(url,parms,type);
                }
            });
        }
    }
	,toPingBi : function(url,parms,flag)
	{
		$.post(url,{ ids : parms , enableFlag : flag},function(result){
			//隐藏提示信息
			$(".tooltip-top").hide(); 
			if (result.status == 0){
				$(adminDo.datagrid_Obj).datagrid('clearSelections');
				$(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
			}
			$.messager.show({    // show error message
				title: adminDo.msgBoxTitle,
				msg: result.message
			});
		},'json');	
	}
    ,cancelTieTab : function(url,parms,type)
    {
        $.post(url,{ ids : parms , type : type},function(result){
            //隐藏提示信息
            $(".tooltip-top").hide();
            if (result.status == 0){
                $(adminDo.datagrid_Obj).datagrid('clearSelections');
                $(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
            }
            $.messager.show({    // show error message
                title: adminDo.msgBoxTitle,
                msg: result.message
            });
        },'json');
    }
	//点击收藏用户事件
	,toShouCangAllUser : function(title,id,index)
	{
		//var allData = $('#dg').datagrid('getData');
		//parent.cacheData.adminDoById = allData.rows[index];
		//alert(JSON.stringify(parent.cacheData));
		adminDo.openNewTab("收藏用户列表 - "+ title,"/HaojzApp/shouCang/jumpWeiJiaoAllUser?faid="+id);
	},
	//点击圈子用户事件
	toQuanZiAllUser : function(title,id,index)
	{
		//adminDo.openNewTab("圈子用户列表 - "+ title,"/HaojzApp/quanziUser/jumpQuanZiUser?id="+id);
		adminDo.openNewTab("圈子用户列表","/HaojzApp/quanziUser/jumpQuanZiUser?id="+id);
	},
	//点击圈子发帖事件
	toQuanZiFaTie : function(title,id,index)
	{
		//adminDo.openNewTab("圈子发帖列表 - "+ title,"/HaojzApp/tie/jumpQuanTieUser?quanziId="+id);
		adminDo.openNewTab("帖子列表","/HaojzApp/tie/jumpQuanAllTie?quanziId="+id);
	},
	//点击好友列表事件
	toFriendList : function(rowData)
	{
		adminDo.openNewTab(rowData.userName+" - 好友列表","/HaojzApp/friend/jumpFriendsList?userid="+rowData.id);
	},
	//激活，停用用户事件
	toUserStatus : function(userid,flag)
	{
		$.post('/HaojzApp/user/update',{ id : parseInt(userid) , enableFlag : parseInt(flag) },function(result){
			if (result.status == 0){
				//隐藏提示信息
				$(".tooltip-top").hide(); 
				//刷新数据
				$(adminDo.datagrid_Obj).datagrid('clearSelections');
				$(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
			} 
			$.messager.show({    // show error message
				title: adminDo.msgBoxTitle,
				msg: result.message
			});
		},'json');
	}
	//添加好友
	,toAddFriend : function(userid,friendid)
	{
		$.post('/HaojzApp/friend/save',{ userId : parseInt(userid) , friendId : parseInt(friendid) },function(result){
			if(result.status == 1)
			{
				$.messager.alert(adminDo.msgBoxTitle,'你们已经是好友关系！');	
			}
			else if (result.status == 0){
				//隐藏提示信息
				$(".tooltip-top").hide(); 
				//刷新数据
				$(adminDo.datagrid_Obj).datagrid('clearSelections');
				$(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
			}
			$.messager.show({    // show error message
				title: adminDo.msgBoxTitle,
				msg: result.message
			});
		},'json');
	}
	//显示标签信息
	,tooltipShow : function(obj)
	{
//		obj.tooltip({  
//			position : "top",
//			onShow: function(){  
//				$(this).tooltip('tip').css({ 
//					/*boxShadow: '1px 1px 3px #292929'      */                    
//				});  
//			}  
//		});  
	},
	//关闭当前Tab
	closetab : function()
	{          
		var tabname = '';
		var p =$(parent.document.getElementById("tabs")).find("li");
		$.each(p, function (i, val) {
				if (val.className == "tabs-selected") {
					tabname=$($(val).find("span")[0]).text();
				}
		});
		window.top.$('#mainContent_tabs').tabs('close', tabname);    
	},
	closetab2 : function(tabname)
	{
		window.top.$('#mainContent_tabs').tabs('close', tabname);	
	}
	,toQueryParams : function(json)
	{
		
	}
	,toChangeState : function(url,no,doc)
	{
		var row = $(doc).find(adminDo.datagrid_Obj).datagrid('getSelections');
		//判断是否选择行
		if (!row || row.length == 0)
		{
			$.messager.alert(adminDo.msgBoxTitle,'请选择您要操作的数据行！','error');	
		}
		else
		{
			$.messager.confirm(adminDo.msgBoxTitle,'你确定操作这些数据吗？',function(r){
				if (r){
					var parms;	
					//循环给提交删除参数赋值	
					$.each(row, function (i, n) 
					{
						if (i == 0) 
						{
							parms = n.id;
						} 
						else 
						{
							parms += "," + n.id;
						}
					});					
					$.post(url,{ ids : parms ,stateNo : no },function(result){
						if (result.status == 0){
							$(doc).find(adminDo.datagrid_Obj).datagrid('clearSelections');
							$(doc).find(adminDo.datagrid_Obj).datagrid('reload');    // reload the user data
						}
						$.messager.show({    // show error message
							title: adminDo.msgBoxTitle,
							msg: result.message
						});
					},'json');
				}
			});
		}
	}
};

//常用方法封装
if(typeof(_myFunction_) == 'undefined') {
    var _myFunction_ = {};
}
_myFunction_ = {
	webUrl : '/HaojzApp',
	arrPic : new Array("jpg","png","gif","apk"), 
	//获取url的参数值
	getQueryString : function(name) 
	{
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
    },
	//正规格式转时间戳
	js_strto_time : function(str_time)
	{
	   var new_str= str_time.replace(/:/g,'-');
	   new_str =new_str.replace(/ /g,'-');
	   var arr =new_str.split("-");
	   var datum =new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4],arr[5]));
	   return strtotime = datum.getTime()/1000;
	   
	},
	//时间戳转正规格式
    js_date_time : function(unixtime)
    {
	   var timestr= new Date(parseInt(unixtime));
	   var datetime= timestr.toLocaleString().replace(/年|月/g, "-").replace(/日/g, "");
	   return datetime;
	},
	js_format_time:function(unixtime){
		var now= new Date(parseInt(unixtime));
	    var   year=now.getYear()+1900;     
        var   month=now.getMonth()+1;     
        var   date=now.getDate();     
        var   hour=now.getHours();     
        var   minute=now.getMinutes();     
        var   second=now.getSeconds();     
        return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;  
	},
	js_strDot_time:function(str){// 类似格式的转换 "2015-01-07 18:44:09.0"
		return str.substring(0,str.indexOf("."));
	},
	//循环遍历数组里面是否有该元素
	isCon : function(arr, val)
	{
		for(var i=0; i<arr.length; i++){
			if(arr[i] == val)
				return true;
		}
		return false;
	},
	//判断文件是否是图片格式
	ChangeInputFile : function(fileObj)
	{
		var divPreviewId = "photoShow_p";
		var imgPreviewId = "photoShow";
		var allowExtention=".jpg,.bmp,.gif,.png,.apk";//允许上传文件的后缀名document.getElementById("hfAllowPicSuffix").value;  
    var extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase();              
    var browserVersion= window.navigator.userAgent.toUpperCase();  
    if(allowExtention.indexOf(extention)>-1){   
        if(fileObj.files){//HTML5实现预览，兼容chrome、火狐7+等  
            if(window.FileReader){  
                var reader = new FileReader();   
                reader.onload = function(e){  
                    document.getElementById(imgPreviewId).setAttribute("src",e.target.result);  
                };
                reader.readAsDataURL(fileObj.files[0]);  
            }else if(browserVersion.indexOf("SAFARI")>-1){  
                alert("不支持Safari6.0以下浏览器的图片预览!");  
            }  
        }else if (browserVersion.indexOf("MSIE")>-1){  
            if(browserVersion.indexOf("MSIE 6")>-1){//ie6  
                document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
            }else{//ie[7-9]  
                fileObj.select();  
                if(browserVersion.indexOf("MSIE 9")>-1)  
                    fileObj.blur();//不加上document.selection.createRange().text在ie9会拒绝访问  
                var newPreview =document.getElementById(divPreviewId+"New");  
                if(newPreview==null){  
                    newPreview =document.createElement("div");  
                    newPreview.setAttribute("id",divPreviewId+"New");  
                    newPreview.style.width = document.getElementById(imgPreviewId).width+"px";  
                    newPreview.style.height = document.getElementById(imgPreviewId).height+"px";  
                    newPreview.style.border="solid 1px #d2e2e2";  
                }  
                newPreview.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";                              
                var tempDivPreview=document.getElementById(divPreviewId);  
                tempDivPreview.parentNode.insertBefore(newPreview,tempDivPreview);  
                tempDivPreview.style.display="none";                      
            }  
        }else if(browserVersion.indexOf("FIREFOX")>-1){//firefox  
            var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);  
            if(firefoxVersion<7){//firefox7以下版本  
                document.getElementById(imgPreviewId).setAttribute("src",fileObj.files[0].getAsDataURL());  
            }else{//firefox7.0+                      
                document.getElementById(imgPreviewId).setAttribute("src",window.URL.createObjectURL(fileObj.files[0]));  
            }  
        }else{  
            document.getElementById(imgPreviewId).setAttribute("src",fileObj.value);  
        }           
    }else{    
		$.messager.alert(adminDo.msgBoxTitle,"仅支持"+allowExtention+"为后缀名的文件!",'error');
        fileObj.value="";//清空选中文件  
        if(browserVersion.indexOf("MSIE")>-1){                          
            fileObj.select();  
            document.selection.clear();  
        }   
		$(".photoShow").show();
        fileObj.outerHTML=fileObj.outerHTML;  
    }
		/*var src=e.target || window.event.srcElement; //获取事件源，兼容chrome/IE
		//alert( src.value );
		//测试chrome浏览器、IE6，获取的文件名带有文件的path路径
		//下面把路径截取为文件名
		var filename=src.value;
		//alert( filename.substring( filename.lastIndexOf('\\')+1 ) );
		//获取文件名的后缀名（文件格式）
		//alert( filename.substring( filename.lastIndexOf('.')+1 ) );
		var name = filename.substring( filename.lastIndexOf('.')+1 );
		if(!_myFunction_.isCon(_myFunction_.arrPic, name))
		{
			src.style.background = 'red';	//显示警告背景
			src.style.color = '#fff';
			src.value = "";		//清空值
			$.messager.alert(adminDo.msgBoxTitle,'您的图片格式不正确，请重新选择！','error');
		}
		else
		{
			src.style.background = '#fff';
			src.style.color = '#222';
			//$(".photoShow").attr("src",filename);	
		}*/
	}
	,isTrue : function(value)
	{
		return parseInt(value) ? '<span style="font-size:26px;color:red;">√</span>' : '<span style="font-size:26px;">×</span>';
	}
	//图片大小不超出父容器
	,imgSF : function(parentBox)
	{
		//容器宽度
		var maxWidth = parentBox.width();
		//循环遍历容器中的图片
		parentBox.find("img").each(function(i)
		{
			var _this = $(this);
			var ni = new Image();
			ni.src = $(this).attr("src");
			ni.onerror = function()
			{
				_this.hide();	
			};
		 ni.onload = function()
			{
				var imgWidth = ni.width;
				var imgHeight = ni.height;
				var maxHeight = maxWidth*imgHeight/imgWidth;
				if(imgWidth > maxWidth)
				{
					_this.css("width",maxWidth * 0.8).css("height",maxHeight);
				}
				_this.show();
			};
		});
	},
	/* 公用的combox加载信息的方法 */
	getComboxValue:function(options,callback){
		COMMON.ajaxFunction({
			url: options.url,
			data : options.data,
			success: function(jsonData){
				jsonData = jsonData.object || jsonData;
				$.isFunction(callback) && callback(jsonData);
		   }
		});
	},
	
	/*
	 * 信息提示
	 * str显示的文字
	 * time信息消失的时间
	 */
	tip: function(str, time) {		
		$.messager.show({
			title: '信息提示',
			msg: str,
			timeout: time,
			style: 'center',
		});
	}
};

$(function()
{
	if($(".photoShow").attr("src") == "")
	{
		$(".photoShow").hide();
	}
});



function checkedIds(strGrid, strField) {
	var checkedRows = $(strGrid).datagrid("getChecked");
	if(checkedRows != null && checkedRows.length > 0) {
		var arr = [];
		$.each(checkedRows, function(idx, obj) {
			if(obj[strField] != undefined) {
				arr.push(obj[strField]);
			}
		});
		return arr.join(",");
	}
}