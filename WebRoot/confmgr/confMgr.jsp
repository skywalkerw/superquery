<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="wjm.query.page.PageUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询配置管理</title>
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
	getAllConfs();
	getAllTables();
	$("#btn_query").click(queryConf);
	$("#btn_edit").click(initEdit);
	$("#btn_test").click(testCurrentConf);
	$("#btn_delete").click(deleteConf);
	$("#btn_save").click(updateConf);
	$("#tablename").change(getAllTableCols);
	$("#btn_addcol").click(addCol);
	//AJAX提交表单
	$("#confform").submit(ajaxSubmit);
});

function addCol(){
	var colname = $("#colname").val();
	var tablename = $("#tablename").val();
	//alert(tablename+"  "+colname);
	$.ajax({
		url : "resp.jsp?action=initAddConfRow&tablename="+tablename+"&colname="+colname,
		async : false,
		success : function(html){
			$("#editconftable").append(html);
			$("a[name='delrow']").show();
			addEvents();
		}
	});
}

function getAllTableCols(){
	var tabname= $(this).val();
	$.ajax({
		url : "resp.jsp?action=getAllTableCols&tablename="+tabname,
		async : false,
		success : function(select){
			$("#colname_td").empty();
			$("#colname_td").append(select);
		}
	});
}

function updateConf(){
	if(!checkUnique("confform","colalias")){
		return false;
	}
	var queryid = $("#queryid").val();
	$("#confform").attr("action",queryid+".cfg?action=updateConf");
	pushValues("queryid");
	pushValues("queryname");
	$("#confform").submit();
}
	
/**给所有name=参数id 的input对象设置值*/
function pushValues(id){
	var val = $("#"+id).val();
	//alert(id+"=="+val);
	$("#confform input[name='"+id+"']").val(val);
}

function testCurrentConf(){
	var queryid = $("#select_queryid").val();
	window.open('<%=request.getContextPath()%>'+"/"+queryid+".qry");
}

function initEdit(){
	$("#editconftable").show();
	$("#btn_save").show();
	$("#addcolumn").show();
	$(this).hide();
	$("#confmgrdiv").hide();
	var queryid = $("#queryid").val();
	var html = $.ajax({
		url : "resp.jsp?action=initModifyConfRow&queryid=" + queryid,
		async : false
	}).responseText;
	$("#editconftable").empty();
	$("#editconftable").append(html);
	$("a[name='delrow']").show();
	addEvents();
	var tablename = $("#tablename").val();
	$.ajax({
		url : "resp.jsp?action=getAllTableCols&tablename="+tablename,
		async : false,
		success : function(select){
			$("#colname_td").empty();
			$("#colname_td").append(select);
		}
	});
	//20130311保护query打头的配置
	if(queryid.indexOf("query_")==0){
		$("#btn_delete").attr("disabled","disabled");
		$("a[name='delrow']").attr("disabled","disabled");
	}else{
		$("#btn_delete").removeAttr("disabled");
		$("a[name='delrow']").removeAttr("disabled");
	}
}

function queryConf() {
	$("#confmgrdiv").show();
	$("#buttons").show();
	$("#btn_edit").show();
	$("#editconftable").hide();
	$("#btn_save").hide();
	$("#addcolumn").hide();
	var queryid = $("#select_queryid").val();
	$("#queryid").val(queryid);
	$.ajax({
		url : "resp.jsp?action=queryConf&queryid=" + queryid,
		async : true,
		success: function(html){
			var head = $("#select_queryid option[value='" + queryid + "']").text();
			$("#confmgrdiv").empty();
			$("#confmgrdiv").append(html);
			$("#queryname").val(head.slice(head.indexOf('-')+1,head.length));
		}
	});
	//20130311保护query打头的配置
	if(queryid.indexOf("query_")==0){
		$("#btn_delete").attr("disabled","disabled");
	}else{
		$("#btn_delete").removeAttr("disabled");
	}
}

function getAllTables(){
	$.ajax({
		url : "resp.jsp?action=getAllTables",
		async : false,
		success : function(select){
			$("#tablename_td").append(select);
		}
	});
}
	
function getAllConfs(){
	$.ajax({
		url : "resp.jsp?action=getAllConfs",
		async : false,
		success : function(select){
			$("#select_queryid").html(select);
		}
	});
}
	
function addEvents(){
	$("a[name='down']").unbind("click");
	$("a[name='down']").click(function(){
		//alert("1");
		var atr = $(this).parents("tr").next();
		$(this).parents("tr").before(atr);
		return false;
	});
	$("a[name='up']").unbind("click");
	$("a[name='up']").click(function(){
		//alert("2");
		var atr = $(this).parents("tr").prev();//找到所在的tr的前一个tr
		if(atr.find("th").length>0){//如果到了列头了，不网上调了
			return false;
		}
		$(this).parents("tr").after(atr);//将前一个tr调整到当前tr的后面
		return false;
	});
	$("a[name='delrow']").unbind("click");
	$("a[name='delrow']").click(deleteRow);
	$("#editconftable input").attr("title","");
	$("#editconftable select").attr("title","");
	$.each($("#editconftable tr"), function(i,tr){
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

function deleteConf(){
	var queryid = $("#queryid").val();
	if(confirm("确认删除当前查询配置["+queryid+"]？")){
		$.ajax({
			url : "resp.jsp?action=deleteConf&queryid=" + queryid,
			async : false,
			success : function(html){
				alert(html);
				$("#btn_query").click();
				getAllConfs();
				return true;
			}
		});
	}else{
		return false;
	}
}

function deleteRow(){
	var queryid = $("#queryid").val();
	var colalias= $(this).parents("tr").find("input[name='colalias']").val();
	var thisa = $(this);
	if(confirm("确认删除当前查询配置["+queryid+"]？")){
		$.ajax({
			url : "resp.jsp?action=deleteConf&queryid=" + queryid+"&colalias="+colalias,
			async : false,
			success : function(html){
				alert(html);
				//alert(thisa.parents("tr").length);
				thisa.parents("tr").remove();
			}
		});
	}
	return false;
}
</script>
</head>
<body>
	<h3>查询配置管理</h3>
	<table>
		<tr>
			<td>查询配置ID</td>
			<td><select id="select_queryid"></select></td>
			<td><input id="btn_query" type="button" value="查询" class="button" ></input></td>
			<td><input id="btn_test" type="button" value="测试" class="button" ></input></td>
			<td><input id="btn_add" type="button" value="新增" class="button"  onclick="return window.open('confGenerate.jsp');"></input></td>
			<td><input id="queryid" type="hidden"></input></td>
		</tr>
	</table>
	<div align="center" id="confmgrdiv" class="clearleft">
	</div>
	<div align="center" class="clearleft">
		<form id="confform" action="" method="post" style="">
			<table id="editconftable" class="output" style="width:100%;display:none;">
			</table>
			<table>
				<tr>
					<td>查询名</td>
					<td><input id="queryname" name="queryname" type="text"></input></td>
					<td>父查询ID</td>
					<td><input id="pqueryid" name="pqueryid" type="text"></input></td>
					<td>查询类型</td>
					<td><input id="querytype" name="querytype" type="text"></input></td>
					<td>备注</td>
					<td><input id="remark" name="remark" type="text"></input></td>
				</tr>
			</table>
		</form>
		<div align="left">
			<table class="output" id="addcolumn" style="width:auto;display:none;padding: 5px;">
				<tr>
					<td class="outputhead" >选择表</td>
					<td class="output"  id="tablename_td"></td>
					<td class="outputhead" >选择列</td>
					<td class="output"  id="colname_td"></td>
					<td class="output" ><input id="btn_addcol" type="button" class="button" value="添加"></input></td>
				</tr>
			</table>
		</div>
		<table id="buttons" style="display:none;">
			<tr>
				<td><input id="btn_delete" type="button" class="button" value="删除当前"></input></td>
				<td><input id="btn_edit" type="button"  class="button"  value="编辑"></input>
				<td><input id="btn_save" type="button"  class="button"  value="保存修改"></input></td>
			</tr>
		</table>
	</div>
</body>
</html>