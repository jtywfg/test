<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
var baseUrl = "<%=basePath%>";
var version = "<%=version%>";
</script>
<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.5/themes/default/easyui.css?v=<%=version%>" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/base.css?v=<%=version%>" />
<link rel="stylesheet" href="<%=basePath%>jquery-easyui-1.5/themes/icon.css" type="text/css" />
<script type="text/javascript" src="<%=basePath%>static/lib/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/config.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>static/lib/layer/layer.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.5/jquery.easyui.min.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/common_data.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>static/js/myfunction.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery-easyui-1.5/loading.js"></script>
<script>
$.ajaxSetup ({
    cache: false //close AJAX cache
});
var easyuiDialogOnMove = function(left, top) { //弹窗查出父级区域 
    if (left < 0) {  
        $(this).dialog('move', {  
            "left" : 1  
        });  
    }  
    if (top < 0) {  
        $(this).dialog('move', {  
            "top" : 1  
        });  
    }  
    var width = $(this).dialog('options').width;  
    var height = $(this).dialog('options').height;  
    var parentWidth = $("#main"/*,parent.document*/).width;  
    var parentHeight = $("#main"/*,parent.document*/).height;  
  
  
  
    if (left > parentWidth - width) {  
        $(this).dialog('move', {  
            "left" : parentWidth - width  
        });  
    }  
    if (top > parentHeight - height) {  
        $(this).dialog('move', {  
            "top" : parentHeight - height  
        });  
    }  
  
  
}  
$.fn.dialog.defaults.onMove = easyuiDialogOnMove;

if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length >>> 0;
    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;
    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}
</script>