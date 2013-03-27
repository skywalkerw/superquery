package wjm.query.page;

import java.io.Serializable;
import java.util.List;

import wjm.common.util.StringUtil;
import wjm.query.meta.TableStructBO;

public class TableStructBean implements Serializable {
	private List<TableStructBO> structs;
	private String tablename;
	private String tablecomment;

	public TableStructBean(List<TableStructBO> struct) {
		this.structs = struct;
		if(structs!=null&&structs.size()>0){
			this.tablename = structs.get(0).getTabname();
			this.tablecomment = structs.get(0).getTabcomment();
			if(StringUtil.isEmpty(this.tablecomment)){
				this.tablecomment = this.tablename;
			}
		}
	}
	
	public String toHtml(){
		StringBuffer ret = new StringBuffer();
		TableStructBO bo ;
		ret.append("<div id='").append(this.tablename).append("' class='floatleft'>");
		ret.append("<table  class='tabstruct'>");
		
		ret.append("<tr>");
		ret.append("<th class='tabstruct' colspan='2'>").append("<input name='selectall' type='checkbox'> </input>").append(this.tablecomment).append("</th>");
		ret.append("<th class='tabstruct' style='text-align: right;'><a class='btn_close' href='#'>X</a></th>");
		ret.append("</tr>");
		for(int i=0;i<this.structs.size();i++){
			bo = this.structs.get(i);
			ret.append("<tr>");
			ret.append("<td class='tabstruct'>").append("<input name='").append(bo.getColname()).append("' type='checkbox' value='").append(bo.getColname()).append("'>").append("</td>");
			ret.append("<td class='tabstruct'>").append(bo.getColname()).append("</td>");
			ret.append("<td class='tabstruct'>").append(bo.getColcnmment()).append("</td>");
			ret.append("</tr>\n");
		}
		ret.append("</table>");
		ret.append("</div>");
		return ret.toString();
	}
}
