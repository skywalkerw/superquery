function first(queryform, pageIndex, pageSize, pageCount) {
	var indexE = document.getElementById(pageIndex);
	indexE.value = 1 - 0;
	document.getElementById(queryform).submit();
}

function prev(queryform, pageIndex, pageSize, pageCount) {
	var indexE = document.getElementById(pageIndex);
	var index = indexE.value - 0;
	if (index > 1) {
		index = index - 1;
	} else {
		return false;
	}
	indexE.value = index;
	document.getElementById(queryform).submit();
}

function next(queryform, pageIndex, pageSize, pageCount) {
	var count = document.getElementById(pageCount).value - 0;
	var indexE = document.getElementById(pageIndex);
	var index = indexE.value - 0;
	if (count > index) {
		index = index + 1;
	} else {
		return false;
	}
	indexE.value = index;
	document.getElementById(queryform).submit();
}

function last(queryform, pageIndex, pageSize, pageCount) {
	var count = document.getElementById(pageCount).value - 0;
	var indexE = document.getElementById(pageIndex);
	indexE.value = count;
	document.getElementById(queryform).submit();
}

function go(queryform, pageIndex, pageSize, pageCount,recordCount) {
	var count = document.getElementById(pageCount).value - 0;
	var indexE = document.getElementById(pageIndex);
	var sizeE = document.getElementById(pageSize);
	var record = document.getElementById(recordCount).value -0 ;
	var size = sizeE.value - 0;
	var index = indexE.value - 0;
	if ( index < 1 || index > (record/size+(record%size==0?0:1))) {
		alert("每页显示"+size+"条指定第"+index+"页不存在,请重新指定!");
		return false;
	}
	document.getElementById(queryform).submit();
}