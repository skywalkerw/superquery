drop table  sys_queryconf;
create  table sys_queryconf(
	queryid varchar2(35) not null,
	querycomment varchar2(60),
	tabname varchar2(35) not null, 
	joinway varchar2(35) , 
	conftype varchar2(16) not null,  
	conforder number(8),  
	colrealname varchar2(35) not null,    
	colalias varchar2(35) not null,
	colcomment varchar2(60),          
	ctrltype varchar2(16),   
	ctrllen number(8),    
	displen number(8),    
	validator varchar(265),    
	disptype varchar2(16),  
	dicttype varchar2(35),  
	css varchar(265),           
	orderby varchar2(16),    
	opper varchar2(16),
	operand varchar2(32),
	ispk varchar2(2)     
);
alter table sys_queryconf add primary key(queryid,colalias);
comment on table sys_queryconf is '查询配置表';
comment on column sys_queryconf.queryid is'查询的ID';
comment on column sys_queryconf.querycomment is'查询说明';
comment on column sys_queryconf.conforder is'配置排序';
comment on column sys_queryconf.conftype is'配置类型';
comment on column sys_queryconf.tabname is'表明';
comment on column sys_queryconf.joinway is'连接方式';
comment on column sys_queryconf.colrealname is'列名(表结构中列名)';
comment on column sys_queryconf.colalias is'列别名(用于生成as语句)';
comment on column sys_queryconf.colcomment is'列注释(结果列列头)';
comment on column sys_queryconf.ctrltype is'控件类型(仅作为输入条件时)';
comment on column sys_queryconf.ctrllen is'控制宽度(仅作为输入条件时)';
comment on column sys_queryconf.displen is'显示宽度(输入时表示控件宽度,结果列表表示列宽)';
comment on column sys_queryconf.validator is'验证';
comment on column sys_queryconf.disptype is'显示类型';
comment on column sys_queryconf.dicttype is'对应字典类别名';
comment on column sys_queryconf.css is'CSS样式表';
comment on column sys_queryconf.orderby is'按改列排序';
comment on column sys_queryconf.opper is'操作符';
comment on column sys_queryconf.operand is'被操作数';
comment on column sys_queryconf.ispk is'查询结果索引';

--insert into sys_queryconf values();

/*==============================================================*/
/* Table: SYS_DICT                                              */
/*==============================================================*/
drop table SYS_DICT;
create table SYS_DICT 
(
   DICID                VARCHAR2(35)         not null,
   DICTTP                VARCHAR2(35)         not null,
   KEY                  VARCHAR2(35)         not null,
   VALUE                VARCHAR2(256),
   PDICTID              VARCHAR2(35),
   SEQNO                NUMBER(8),
   constraint PK_SYS_DICT primary key (DICTTP,DICID)
);

comment on table SYS_DICT is'数据字典表';
comment on column SYS_DICT.DICID is'主键';
comment on column SYS_DICT.DICTTP is'字典分组';
comment on column SYS_DICT.KEY is'字典值';
comment on column SYS_DICT.VALUE is'字典说明';
comment on column SYS_DICT.PDICTID is'父级节点';
comment on column SYS_DICT.SEQNO is'指定顺序，同group里面生效';

/*==============================================================*/
/* Index: FK_DICT_DICTGROUP_FK                                  */
/*==============================================================*/
create index FK_DICT_DICTGROUP_FK on SYS_DICT (
   DICTTP ASC
);

/*==============================================================*/
/* Table: SYS_DICTTP                                            */
/*==============================================================*/
drop table SYS_DICTTP;
create table SYS_DICTTP 
(
   DICTTP                VARCHAR2(35)         not null,
   TPNAME                 VARCHAR2(256),
   PGROUPID             VARCHAR2(35),
   SEQNO                NUMBER(8),
   constraint PK_SYS_DICTTP primary key (DICTTP)
);

comment on table SYS_DICTTP is'字典分组表';
comment on column SYS_DICTTP.DICTTP is'字典分组名';
comment on column SYS_DICTTP.TPNAME is'字典分组名称';
comment on column SYS_DICTTP.PGROUPID is'父级字典分组';
comment on column SYS_DICTTP.SEQNO is'排序用，整表生效';