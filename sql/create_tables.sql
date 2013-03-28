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
comment on table sys_queryconf is '��ѯ���ñ�';
comment on column sys_queryconf.queryid is'��ѯ��ID';
comment on column sys_queryconf.querycomment is'��ѯ˵��';
comment on column sys_queryconf.conforder is'��������';
comment on column sys_queryconf.conftype is'��������';
comment on column sys_queryconf.tabname is'����';
comment on column sys_queryconf.joinway is'���ӷ�ʽ';
comment on column sys_queryconf.colrealname is'����(��ṹ������)';
comment on column sys_queryconf.colalias is'�б���(��������as���)';
comment on column sys_queryconf.colcomment is'��ע��(�������ͷ)';
comment on column sys_queryconf.ctrltype is'�ؼ�����(����Ϊ��������ʱ)';
comment on column sys_queryconf.ctrllen is'���ƿ��(����Ϊ��������ʱ)';
comment on column sys_queryconf.displen is'��ʾ���(����ʱ��ʾ�ؼ����,����б��ʾ�п�)';
comment on column sys_queryconf.validator is'��֤';
comment on column sys_queryconf.disptype is'��ʾ����';
comment on column sys_queryconf.dicttype is'��Ӧ�ֵ������';
comment on column sys_queryconf.css is'CSS��ʽ��';
comment on column sys_queryconf.orderby is'����������';
comment on column sys_queryconf.opper is'������';
comment on column sys_queryconf.operand is'��������';
comment on column sys_queryconf.ispk is'��ѯ�������';

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

comment on table SYS_DICT is'�����ֵ��';
comment on column SYS_DICT.DICID is'����';
comment on column SYS_DICT.DICTTP is'�ֵ����';
comment on column SYS_DICT.KEY is'�ֵ�ֵ';
comment on column SYS_DICT.VALUE is'�ֵ�˵��';
comment on column SYS_DICT.PDICTID is'�����ڵ�';
comment on column SYS_DICT.SEQNO is'ָ��˳��ͬgroup������Ч';

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

comment on table SYS_DICTTP is'�ֵ�����';
comment on column SYS_DICTTP.DICTTP is'�ֵ������';
comment on column SYS_DICTTP.TPNAME is'�ֵ��������';
comment on column SYS_DICTTP.PGROUPID is'�����ֵ����';
comment on column SYS_DICTTP.SEQNO is'�����ã�������Ч';