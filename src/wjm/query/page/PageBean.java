package wjm.query.page;

import java.io.Serializable;

import wjm.common.util.QConst;

public class PageBean implements Serializable {
	private int pageIndex = 1;// 当前页
	private int pageSize = 20;// 每页记录数
	private int recordCount = 0;// 总记录数
	private int pageCount = 0;// 总页数

	public PageBean(int pageIndex,int pageSize,int recordCount){
		this.pageSize = pageSize;
		this.recordCount = recordCount;
		this.pageCount = (recordCount%pageSize==0?0:1)+(recordCount/pageSize);
		this.pageIndex = pageIndex;
	}
	
	
	public String toHtml(){
		StringBuffer ret = new StringBuffer();
		ret.append("<div>");
		ret.append("共");
		ret.append(recordCount);
		ret.append("<input name='recordCount' id='recordCount' type='hidden' style='width:100px;' value='").append(recordCount).append("'></input>");
		ret.append("条");
		
		ret.append("/");
		ret.append(pageCount);
		ret.append("<input name='pageCount' id='pageCount' type='hidden' style='width:100px;' value='").append(pageCount).append("'></input>");
		ret.append("页&nbsp;");
		
		ret.append("当前第");
		ret.append(pageIndex);
		ret.append("页&nbsp;");
		ret.append("<input type='button' class='button' value='<<' onclick=\"first('queryform','pageIndex','pageSize','pageCount');\"></input>");
		ret.append("<input type='button' class='button' value='<' onclick=\"prev('queryform','pageIndex','pageSize','pageCount');\"></input>");
		ret.append("<input type='button' class='button' value='>' onclick=\"next('queryform','pageIndex','pageSize','pageCount');\"></input>");
		ret.append("<input type='button' class='button' value='>>' onclick=\"last('queryform','pageIndex','pageSize','pageCount');\"></input>");
		ret.append("每页显示");
		ret.append("<input name='pageSize' id='pageSize' type='text' style='width:100px;' value='").append(pageSize).append("'></input>");
		ret.append("条&nbsp;");
		ret.append("转到第");
		ret.append("<input name='pageIndex' id='pageIndex' type='text'  style='width:100px;' value='").append(pageIndex).append("'></input>");
		ret.append("页&nbsp;");
		ret.append("<input type='button' class='button' value='GO' onclick=\"go('queryform','pageIndex','pageSize','pageCount','recordCount');\"></input>");
		ret.append("</div>");
		ret.append("<script type='text/javascript' src='").append(QConst.CONTEXTPATH).append("/res/script/pager.js'></script>");
		return ret.toString();
	}

}
