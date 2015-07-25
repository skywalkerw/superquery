prompt PL/SQL Developer import file
prompt Created on 2015年7月26日 by Administrator
set feedback off
set define off
prompt Disabling triggers for QUERYPLUS.SYS_DICTTP...
alter table QUERYPLUS.SYS_DICTTP disable all triggers;
prompt Disabling triggers for QUERYPLUS.SYS_DICT...
alter table QUERYPLUS.SYS_DICT disable all triggers;
prompt Disabling triggers for QUERYPLUS.SYS_QUERY...
alter table QUERYPLUS.SYS_QUERY disable all triggers;
prompt Disabling triggers for QUERYPLUS.SYS_QUERYFIELD...
alter table QUERYPLUS.SYS_QUERYFIELD disable all triggers;
prompt Disabling foreign key constraints for QUERYPLUS.SYS_DICT...
alter table QUERYPLUS.SYS_DICT disable constraint SYS_C0017113;
prompt Disabling foreign key constraints for QUERYPLUS.SYS_QUERYFIELD...
alter table QUERYPLUS.SYS_QUERYFIELD disable constraint FK_FIELD_DEF;
prompt Deleting QUERYPLUS.SYS_QUERYFIELD...
delete from QUERYPLUS.SYS_QUERYFIELD;
commit;
prompt Deleting QUERYPLUS.SYS_QUERY...
delete from QUERYPLUS.SYS_QUERY;
commit;
prompt Deleting QUERYPLUS.SYS_DICT...
delete from QUERYPLUS.SYS_DICT;
commit;
prompt Deleting QUERYPLUS.SYS_DICTTP...
delete from QUERYPLUS.SYS_DICTTP;
commit;
prompt Loading QUERYPLUS.SYS_DICTTP...
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('fieldtype', '配置类型', null, 1);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('ctrltype', '控件类型', null, 2);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('validator', '验证类型', null, 3);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('opper', '操作符', null, 4);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('joinway', 'JOIN类型', null, 5);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('orderby', '排序方式', null, 6);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('disptype', '显示类型', null, 7);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('ispk', '是否为结果列主键', null, 8);
commit;
prompt 8 records loaded
prompt Loading QUERYPLUS.SYS_DICT...
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('001', 'fieldtype', 'condition', '查询条件', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('002', 'fieldtype', 'output', '输出', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('003', 'fieldtype', 'both', '条件和输出', null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('004', 'ctrltype', 'text', '文本框', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('005', 'ctrltype', 'select', '下拉框', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('006', 'validator', 'NotNull', '不为空', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('007', 'opper', '=', null, null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('008', 'opper', '<', null, null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('009', 'opper', '>', null, null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('010', 'opper', '<=', null, null, 5);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('011', 'opper', '%like%', null, null, 6);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('012', 'opper', '%like', null, null, 7);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('013', 'opper', 'like%', null, null, 8);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('014', 'joinway', 'left', '左联接', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('015', 'joinway', 'right', '右联接', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('016', 'joinway', 'full', '全联接', null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('017', 'orderby', 'asc', '升序', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('018', 'orderby', 'desc', '降序', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('019', 'disptype', 'plain', '普通文本', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('020', 'disptype', 'dict', '字典', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('020', 'opper', '>=', null, null, 4);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('021', 'disptype', 'none', '不显示', null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('023', 'ispk', '1', '是', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('024', 'ispk', '0', '否', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('022', 'ctrltype', 'none', '不显示', null, 3);
commit;
prompt 25 records loaded
prompt Loading QUERYPLUS.SYS_QUERY...
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_tabstruct', '系统内置查询-查表结构', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_queryconf', '系统内置查询-配置表', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('test1111', '测试用配置', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_alltables', '系统内置查询-列出所有表', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('test_dict', '查询测试', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_dict', '查询数据字典', null, null);
commit;
prompt 6 records loaded
prompt Loading QUERYPLUS.SYS_QUERYFIELD...
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 20, 'ISPK', 'ISPK', '查询结果索引', 'text', 2, 2, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICTTP', null, 'both', 2, 'DICTTP', 'DICTTP', '字典分组名', 'text', 35, 35, null, 'plain', null, null, 'asc', '%like%', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICTTP', null, 'both', 5, 'TPNAME', 'TPNAME', '字典分组名称', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', null, 'both', 1, 'DICID', 'DICID', '主键', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', 'full', 'both', 3, 'DICTTP', 'FKDICTTP', '字典分组', 'none', 35, 35, null, 'plain', null, null, null, '=', 'DICTTP', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', null, 'both', 4, 'KEY', 'KEY', '字典值', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', null, 'both', 6, 'VALUE', 'VALUE', '字典说明', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 1, 'QUERYID', 'QUERYID', '查询的ID', 'text', 35, 35, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_query', null, 'both', 2, 'queryname', 'queryname', '查询名', 'text', 60, 60, null, 'plain', null, null, null, '%like%', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 3, 'TABNAME', 'TABNAME', '表明', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 4, 'JOINWAY', 'JOINWAY', '连接方式', 'text', 35, 35, null, 'dict', 'joinway', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 5, 'FIELDTYPE', 'FIELDTYPE', '配置类型', 'text', 16, 16, null, 'dict', 'fieldtype', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 6, 'FIELDORDER', 'FIELDORDER', '配置排序', 'text', 22, 22, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 7, 'COLREALNAME', 'COLREALNAME', '列名', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 8, 'COLALIAS', 'COLALIAS', '列别名', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 9, 'COLCOMMENT', 'COLCOMMENT', '列注释', 'text', 60, 60, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 10, 'CTRLTYPE', 'CTRLTYPE', '控件类型', 'text', 16, 16, null, 'dict', 'ctrltype', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 11, 'CTRLLEN', 'CTRLLEN', '控制宽度', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 12, 'DISPLEN', 'DISPLEN', '显示宽度', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 13, 'DISPTYPE', 'DISPTYPE', '显示类型', 'text', 16, 16, null, 'dict', 'disptype', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 14, 'DICTTYPE', 'DICTTYPE', '对应字典类别名', 'text', 35, 35, null, 'dict', 'alldicttypes', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 15, 'CSS', 'CSS', 'CSS样式表', 'text', 265, 265, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 16, 'ORDERBY', 'ORDERBY', '按改列排序', 'text', 16, 16, null, 'dict', 'orderby', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 17, 'OPPER', 'OPPER', '操作符', 'text', 16, 16, null, 'dict', 'opper', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 18, 'OPERAND', 'OPERAND', '被操作数', 'text', 32, 32, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 19, 'VALIDATOR', 'VALIDATOR', '验证', 'text', 256, 256, null, 'dict', 'validator', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 1, 'DICID', 'DICID', '主键', 'text', 35, 35, null, 'plain', null, null, null, '=', null, '1');
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 2, 'DICTTP', 'DICTTP', '字典分组', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 4, 'KEY', 'KEY', '字典值', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 5, 'VALUE', 'VALUE', '字典说明', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 6, 'PDICTID', 'PDICTID', '父级节点', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 7, 'SEQNO', 'SEQNO', '同组排序', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICTTP', null, 'both', 3, 'TPNAME', 'TPNAME', '字典分组名称', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICTTP', 'right', 'condition', 8, 'DICTTP', 'DICTTP1', '字典分组名1', null, 35, 35, null, 'none', null, null, null, '=', 'DICTTP', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_alltables', 'all_tab_comments', null, 'condition', 1, 'owner', 'owner', null, null, null, 100, null, null, null, null, null, '=', '#QUERYPLUS', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_alltables', 'all_tab_comments', null, 'both', 2, 'table_name', 'key', '表明称', 'text', 100, 100, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_alltables', 'all_tab_comments', null, 'both', 3, 'comments', 'value', '表注释', 'text', 100, 100, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', 'full', 'both', 1, 'table_name', 'tabname', '表明称', 'text', 32, 32, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_comments', null, 'output', 2, 'comments', 'tabcomment', '表注释', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 3, 'column_id', 'colid', '列ID', null, null, 32, null, 'plain', null, null, 'asc', null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', 'full', 'both', 4, 'column_name', 'colname', '列名', 'text', null, 32, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 5, 'data_type', 'datatype', '数据类型', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 6, 'data_length', 'datalength', '数据长度', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 7, 'data_precision', 'dataprecision', '整数长度', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 8, 'data_scale', 'datascale', '小数长度', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_col_comments', null, 'output', 9, 'comments', 'colcnmment', '列注释', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_col_comments', 'full', 'condition', 10, 'table_name', 'tabname1', null, null, null, null, null, null, null, null, null, '=', 'tabname', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_comments', 'full', 'condition', 11, 'table_name', 'tabname2', null, null, null, null, null, null, null, null, null, '=', 'tabname', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_col_comments', 'full', 'condition', 12, 'column_name', 'colname1', null, null, null, null, null, null, null, null, null, '=', 'colname', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_col_comments', 'full', 'condition', 13, 'owner', 'owner', null, null, null, null, null, null, null, null, null, '=', '#QUERYPLUS', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', 'full', 'condition', 14, 'owner', 'owner1', null, null, null, null, null, null, null, null, null, '=', 'owner', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_comments', 'full', 'condition', 15, 'owner', 'owner2', null, null, null, null, null, null, null, null, null, '=', 'owner', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 2, 'DICTTP', 'DICTTP', '字典分组', 'text', 35, 35, null, 'plain', null, null, null, '=', null, '1');
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 3, 'KEY', 'KEY', '字典值', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 4, 'VALUE', 'VALUE', '字典说明', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 5, 'PDICTID', 'PDICTID', '父级节点', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 6, 'SEQNO', 'SEQNO', '指定顺序，同group里面生效', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'condition', 1, 'DICID', 'DICID', '主键', 'text', 35, 35, null, 'plain', null, null, null, '=', null, '1');
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_query', 'left', 'condition', 21, 'QUERYID', 'QUERYID1', '查询ID', 'none', 60, 60, null, 'none', null, null, null, '=', 'QUERYID', null);
commit;
prompt 59 records loaded
prompt Enabling foreign key constraints for QUERYPLUS.SYS_DICT...
alter table QUERYPLUS.SYS_DICT enable constraint SYS_C0017113;
prompt Enabling foreign key constraints for QUERYPLUS.SYS_QUERYFIELD...
alter table QUERYPLUS.SYS_QUERYFIELD enable constraint FK_FIELD_DEF;
prompt Enabling triggers for QUERYPLUS.SYS_DICTTP...
alter table QUERYPLUS.SYS_DICTTP enable all triggers;
prompt Enabling triggers for QUERYPLUS.SYS_DICT...
alter table QUERYPLUS.SYS_DICT enable all triggers;
prompt Enabling triggers for QUERYPLUS.SYS_QUERY...
alter table QUERYPLUS.SYS_QUERY enable all triggers;
prompt Enabling triggers for QUERYPLUS.SYS_QUERYFIELD...
alter table QUERYPLUS.SYS_QUERYFIELD enable all triggers;
set feedback on
set define on
prompt Done.
