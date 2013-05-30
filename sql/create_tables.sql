drop table  sys_queryfield;
drop table sys_query;
drop table sys_dict;
drop table sys_dicttp;

create table sys_query(
	queryid varchar2(35) primary key,
	queryname varchar2(60),
	pqueryid varchar2(35),
	querytype varchar2(10),
	remark varchar(256)
);
comment on table sys_query is '查询配置表';
comment on column sys_query.queryid is'查询的ID';
comment on column sys_query.queryname is'查询中文名';
comment on column sys_query.pqueryid is'父查询ID';
comment on column sys_query.querytype is'查询类型（系统，用户）';
comment on column sys_query.remark is'备注';

create  table sys_queryfield(
	queryid varchar2(35) constraint fk_field_def references sys_query(queryid),
	tabname varchar2(35) not null, 
	joinway varchar2(35), 
	fieldtype varchar2(16) not null,  
	fieldorder number(8),  
	colrealname varchar2(35) not null,    
	colalias varchar2(35) not null,
	colcomment varchar2(60),          
	ctrltype varchar2(16),   
	ctrllen number(8),    
	displen number(8),    
	validator varchar2(256),    
	disptype varchar2(16),  
	dicttype varchar2(35),  
	css varchar2(256),           
	orderby varchar2(16),    
	opper varchar2(16),
	operand varchar2(32),
	ispk varchar2(2)     
);
alter table sys_queryfield add primary key(queryid,colalias);
comment on table sys_queryfield is '查询配置域子表';
comment on column sys_queryfield.queryid is'查询的ID';
comment on column sys_queryfield.fieldorder is'配置排序';
comment on column sys_queryfield.fieldtype is'配置类型';
comment on column sys_queryfield.tabname is'表明';
comment on column sys_queryfield.joinway is'连接方式';
comment on column sys_queryfield.colrealname is'列名(表结构中列名)';
comment on column sys_queryfield.colalias is'列别名(用于生成as语句)';
comment on column sys_queryfield.colcomment is'列注释(结果列列头)';
comment on column sys_queryfield.ctrltype is'控件类型(仅作为输入条件时)';
comment on column sys_queryfield.ctrllen is'控制宽度(仅作为输入条件时)';
comment on column sys_queryfield.displen is'显示宽度(输入时表示控件宽度,结果列表表示列宽)';
comment on column sys_queryfield.validator is'验证';
comment on column sys_queryfield.disptype is'显示类型';
comment on column sys_queryfield.dicttype is'对应字典类别名';
comment on column sys_queryfield.css is'css样式表';
comment on column sys_queryfield.orderby is'按改列排序';
comment on column sys_queryfield.opper is'操作符';
comment on column sys_queryfield.operand is'被操作数';
comment on column sys_queryfield.ispk is'查询结果索引';

--insert into sys_queryfield values();

/*==============================================================*/
/* table: sys_dict                                              */
/*==============================================================*/
create table sys_dict 
(
   dicid                varchar2(35)         not null,
   dicttp                varchar2(35)         not null constraint fk_dict_type references sys_dicttp(dicttp),
   key                  varchar2(35)         not null,
   value                varchar2(256),
   pdictid              varchar2(35),
   seqno                number(8),
   constraint pk_sys_dict primary key (dicttp,dicid)
);

comment on table sys_dict is'数据字典表';
comment on column sys_dict.dicid is'主键';
comment on column sys_dict.dicttp is'字典分组';
comment on column sys_dict.key is'字典值';
comment on column sys_dict.value is'字典说明';
comment on column sys_dict.pdictid is'父级节点';
comment on column sys_dict.seqno is'指定顺序，同group里面生效';

/*==============================================================*/
/* index: fk_dict_dictgroup_fk                                  */
/*==============================================================*/
create index fk_dict_dictgroup_fk on sys_dict (
   dicttp asc
);

/*==============================================================*/
/* table: sys_dicttp                                            */
/*==============================================================*/
create table sys_dicttp 
(
   dicttp                varchar2(35)         not null,
   tpname                 varchar2(256),
   pgroupid             varchar2(35),
   seqno                number(8),
   constraint pk_sys_dicttp primary key (dicttp)
);

comment on table sys_dicttp is'字典分组表';
comment on column sys_dicttp.dicttp is'字典分组名';
comment on column sys_dicttp.tpname is'字典分组名称';
comment on column sys_dicttp.pgroupid is'父级字典分组';
comment on column sys_dicttp.seqno is'排序用，整表生效';