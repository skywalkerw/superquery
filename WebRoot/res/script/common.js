/**
 * 检查form 同name的input值是否重复
 */
function checkUnique(form, name) {
	var alias = $("#" + form + " input[name='" + name + "']");
	// alert(alias.length);
	var ret = true;
	$.each(alias, function(i, ali) {
		var val = $(ali).val();
		var count = 0;
		$.each(alias, function(j, alj) {
			// TODO 测试大小写不敏感是否正确
			if (val.toLowerCase() == $(alj).val().toLowerCase()) {
				count++;
				if (count > 1) {
					$(alj).parents("tr").css("background-color", "#FFFF00");
					$(alj).css("color", "red");
				}
			}
		});
		if (count > 1) {
			alert("列别名[" + val + "]重复（大小写不敏感）");
			$(ali).parents("tr").css("background-color", "#FFFF00");
			$(ali).css("color", "red");
			ret = false;
			return ret;
		} else {
			$(ali).parents("tr").css("background-color", "white");
			$(ali).css("color", "black");
		}
	});
	return ret;
}
/**
 * Ajax方式提交表单
 * */
function ajaxSubmit(formname) {
	//alert("submit!");
	var form = $("#"+formname);
	$.post(
		form.attr("action"),
		form.serialize(),
		function (result,status){
			if(status=="success"){
				alert(result);
			}else{
				alert("数据提交出错："+result);
			}
		},"text");
	return false;
}