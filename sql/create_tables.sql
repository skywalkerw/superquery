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
comment on table sys_query is '��ѯ���ñ�';
comment on column sys_query.queryid is'��ѯ��ID';
comment on column sys_query.queryname is'��ѯ������';
comment on column sys_query.pqueryid is'����ѯID';
comment on column sys_query.querytype is'��ѯ���ͣ�ϵͳ���û���';
comment on column sys_query.remark is'��ע';

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
comment on table sys_queryfield is '��ѯ�������ӱ�';
comment on column sys_queryfield.queryid is'��ѯ��ID';
comment on column sys_queryfield.fieldorder is'��������';
comment on column sys_queryfield.fieldtype is'��������';
comment on column sys_queryfield.tabname is'����';
comment on column sys_queryfield.joinway is'���ӷ�ʽ';
comment on column sys_queryfield.colrealname is'����(��ṹ������)';
comment on column sys_queryfield.colalias is'�б���(��������as���)';
comment on column sys_queryfield.colcomment is'��ע��(�������ͷ)';
comment on column sys_queryfield.ctrltype is'�ؼ�����(����Ϊ��������ʱ)';
comment on column sys_queryfield.ctrllen is'���ƿ��(����Ϊ��������ʱ)';
comment on column sys_queryfield.displen is'��ʾ���(����ʱ��ʾ�ؼ����,����б��ʾ�п�)';
comment on column sys_queryfield.validator is'��֤';
comment on column sys_queryfield.disptype is'��ʾ����';
comment on column sys_queryfield.dicttype is'��Ӧ�ֵ������';
comment on column sys_queryfield.css is'css��ʽ��';
comment on column sys_queryfield.orderby is'����������';
comment on column sys_queryfield.opper is'������';
comment on column sys_queryfield.operand is'��������';
comment on column sys_queryfield.ispk is'��ѯ�������';

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

comment on table sys_dict is'�����ֵ��';
comment on column sys_dict.dicid is'����';
comment on column sys_dict.dicttp is'�ֵ����';
comment on column sys_dict.key is'�ֵ�ֵ';
comment on column sys_dict.value is'�ֵ�˵��';
comment on column sys_dict.pdictid is'�����ڵ�';
comment on column sys_dict.seqno is'ָ��˳��ͬgroup������Ч';

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

comment on table sys_dicttp is'�ֵ�����';
comment on column sys_dicttp.dicttp is'�ֵ������';
comment on column sys_dicttp.tpname is'�ֵ��������';
comment on column sys_dicttp.pgroupid is'�����ֵ����';
comment on column sys_dicttp.seqno is'�����ã�������Ч';