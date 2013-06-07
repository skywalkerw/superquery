<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询配置生成</title>
<jsp:include page="/res/include/common.jsp" ></jsp:include>
<script type="text/javascript">
$(document).ready(function() {
	$.ajaxSetup({cache: false });
	$( document ).tooltip({
		position: {
			my: "left top",
			at: "left bottom",
			using: function( position, feedback ) {
				$( this ).css( position );
				$( "<div>" )
					.addClass( "arrow" )
					.addClass( feedback.vertical )
					.addClass( feedback.horizontal )
					.appendTo( this );
			}
		}
	});
	/*初始化表名下拉框*/
	$.ajax({
		url : "resp.jsp?action=getAllTables",
		async : false,
		success : function(select){
			$("#addtable").before(select);
		}
	});
	
	addEvents();
	/*添加表按钮事件，获取表结构并加入container*/
	$("#addtable").click(addTable);
	/*提交按钮处理*/
	$("#save_conf").click(saveConf);
	//AJAX提交表单
	$("#confform").submit(ajaxSubmit);
	
});

/**给新增的表结构div添加事件**/
function addEvents(){
	$(".btn_close:last").click(function(){
		var ctrl = $(this).parents(".floatleft");
		var tablename = ctrl.attr("id");
		//alert(tablename);
		//去掉所有所选择的该表的列
		$("#confform input[name='tabname'][value='"+tablename+"']").parents("tr").remove();
		//去掉表结构div
		ctrl.remove();
		return false;
	});
	//全选按钮事件
	$(".floatleft:last input[name='selectall']").click(selectAll);
	$(".floatleft:last input[type='checkbox']").click(selectOne);
}

function addTable(){
	var tablename = $("#tablename").val();
	//alert(tablename);
	if($("#"+tablename).length>0){
		alert("表"+tablename+"已经被添加!");
		return ;
	}
	$.ajax({
		url : "resp.jsp?action=getTableStruct&tablename="+tablename,
		async : false,
		success : function(html){
			$("#container").append(html);
			addEvents();
		}
	});
}

function saveConf(){
	var queryid = $("#queryid").val();
	if($("#queryconftable tr").length<=1){
		alert("您还没有配置任何列！请选定表然后点击\"添加表\"按钮进行添加");
		return false;
	}
	if(queryid==""){
		alert("请填写\"查询ID\"!");
		return false;
	}
	//验证查询配置id开头是否为query_
	if(queryid.indexOf("query_")==0){
		if(!confirm("\"query_\"开头的配置为系统查询["+queryid+"]将不能被删除，是否确认提交？"))
			return false;	
	}
	if(!checkUnique("confform","colalias")){
		return false;
	}
	if(!checkQueryid(queryid)){
		return false;
	}
	//alert("提交查询ID："+queryid);
	$("#confform").attr("action",queryid+".cfg?action=addQueryConf");
	//queryid和queryname不在form里面
	//提交的时候需要给hidden的queryid和queryname设置值
	//问为什么，因为要保证提交的参数是整齐的(一张表)
	pushValues("queryid");
	pushValues("queryname");
	$("#confform").submit();
}

function selectAll(){
	var selects = $(this).parents(".floatleft").find("input[type='checkbox']").not($(this));
	var tablename = $(this).parents(".floatleft").attr("id");
	var confs = $("#confform input[name='tabname'][value='"+tablename+"']").parents("tr");
	if($(this).attr("checked")){
		confs.remove();
		selects.attr("checked","checked");
		$.ajax({
			url : "resp.jsp?action=batchInitAddConfRow&tablename="+tablename,
			async : false,
			success : function(html){
				$("#queryconftable").append(html);
			}
		});
	}else{
		selects.removeAttr("checked");
		confs.remove();
	}
}

function selectOne(){
	var colname = $(this).val();
	var tablename = $(this).parents(".floatleft").attr("id");
	if($(this).attr("checked")){
		//alert(tablename+"."+colname);
		$.ajax({
			url : "resp.jsp?action=initAddConfRow&tablename="+tablename+"&colname="+colname,
			async : false,
			success : function(html){
				$("#queryconftable").append(html);
				//给箭头添加事件
				$("a[name='down']").unbind("click");
				$("a[name='up']").unbind("click");
				$("a[name='down']").click(function(){
					//alert("1");
					var atr = $(this).parents("tr").next();
					$(this).parents("tr").before(atr);
					return false;
				});
				$("a[name='up']").click(function(){
					//alert("2");
					var atr = $(this).parents("tr").prev();//找到所在的tr的前一个tr
					if(atr.find("th").length>0){//如果到了列头了，不网上调了
						return false;
					}
					$(this).parents("tr").after(atr);//将前一个tr调整到当前tr的后面
					return false;
				});
				$("#queryconftable input").attr("title","");
				$("#queryconftable select").attr("title","");
				$.each($("#queryconftable tr"), function(i,tr){
					var msg = "当前配置";
					msg += "<br/>表明：";
					msg += $(tr).find("input[name='tabname']").val();
					msg += "<br/>列名：";
					msg += $(tr).find("input[name='colrealname']").val();
					msg += "<br/>注释：";
					msg += $(tr).find("input[name='colcomment']").val();
					$(tr).find("input").attr("title",msg);
					//$(tr).find("select").attr("title",msg);
				});
			}
		});
	}else{
		var trs = $("#confform input[name='colrealname'][value='"+colname+"']").parents("tr");
		$.each(trs,function(i,tr){
			//alert(""+tr);
			var inp = $(tr).find("input[name='tabname'][value='"+tablename+"']");
			//alert(inp.length );
			if(inp&&inp.length > 0){
				$(tr).remove();
			}
		});
	}
}

/**给所有name=参数id 的input对象设置值*/
function pushValues(id){
	var val = $("#"+id).val();
	//alert(id+"=="+val);
	$("#confform input[name='"+id+"']").val(val);
}
function checkQueryid(queryid){
	var count = $.ajax({
		url : "resp.jsp?action=checkQueryid&queryid="+queryid,
		async : false
	}).responseText;
	if(count-0>=1){
		alert("查询ID["+queryid+"]已存在,请修改！");
		$("#queryid").css("color","red");
		return false;
	}
	$("#queryid").css("color","black");
	return true;
}

</script>
</head>
<body>
	<h3>查询配置生成</h3>
	<div id='ajaxwait' style='display:none;background-color:gray;position:absolute;left:40%;top:40%;width:20%;height:20%;z-index:1'>ajax</div>
	<div class="clearleft">
		<input id="addtable" type="button" value="添加表" title="选择一张表并点击此按钮"  class="button"></input>
	</div>
	<div id="container"></div>
	<form id="confform" action="" method="post">
		<div class="clearleft">
			<table id="queryconftable" class="output" style="width:100%;">
				<tr>
					<th class="output">调序</th>
					<th class="output">表名</th>
					<th class="output">列名</th>
					<th class="output">列注释</th>
					<th class="output">列别名</th>
					<th class="output">配置类别</th>
					<th class="output">控件类型</th>
					<th class="output">控制输入长度</th>
					<th class="output">控件显示长度</th>
					<th class="output">输入验证</th>
					<th class="output">操作符</th>
					<th class="output">被操作数</th>
					<th class="output">JOIN类型</th>
					<th class="output">排序</th>
					<th class="output">数据字典类别</th>
					<th class="output">显示类型</th>
					<th class="output">CSS样式</th>
					<th class="output">结果唯一索引</th>
				</tr>
			</table>
		</div>
		<div align="center" id="queryid_name"  class="clearleft">
			<table>
				<tr>
					<td>查询ID</td><!--  name="queryid"  -->
					<td><input id="queryid" type="text"></input></td>
					<td>查询名</td>
					<td><input id="queryname" name="queryname" type="text"></input></td>
					<td>父查询ID</td>
					<td><input id="pqueryid" name="pqueryid" type="text"></input></td>
					<td>查询类型</td>
					<td><input id="querytype" name="querytype" type="text"></input></td>
					<td>备注</td>
					<td><input id="remark" name="remark" type="text"></input></td>
					<td><input id="save_conf" type="submit" value="提交" class="button"></input></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>