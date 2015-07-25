prompt PL/SQL Developer import file
prompt Created on 2015��7��26�� by Administrator
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
values ('fieldtype', '��������', null, 1);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('ctrltype', '�ؼ�����', null, 2);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('validator', '��֤����', null, 3);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('opper', '������', null, 4);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('joinway', 'JOIN����', null, 5);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('orderby', '����ʽ', null, 6);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('disptype', '��ʾ����', null, 7);
insert into QUERYPLUS.SYS_DICTTP (dicttp, tpname, pgroupid, seqno)
values ('ispk', '�Ƿ�Ϊ���������', null, 8);
commit;
prompt 8 records loaded
prompt Loading QUERYPLUS.SYS_DICT...
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('001', 'fieldtype', 'condition', '��ѯ����', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('002', 'fieldtype', 'output', '���', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('003', 'fieldtype', 'both', '���������', null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('004', 'ctrltype', 'text', '�ı���', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('005', 'ctrltype', 'select', '������', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('006', 'validator', 'NotNull', '��Ϊ��', null, 1);
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
values ('014', 'joinway', 'left', '������', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('015', 'joinway', 'right', '������', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('016', 'joinway', 'full', 'ȫ����', null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('017', 'orderby', 'asc', '����', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('018', 'orderby', 'desc', '����', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('019', 'disptype', 'plain', '��ͨ�ı�', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('020', 'disptype', 'dict', '�ֵ�', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('020', 'opper', '>=', null, null, 4);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('021', 'disptype', 'none', '����ʾ', null, 3);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('023', 'ispk', '1', '��', null, 1);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('024', 'ispk', '0', '��', null, 2);
insert into QUERYPLUS.SYS_DICT (dicid, dicttp, key, value, pdictid, seqno)
values ('022', 'ctrltype', 'none', '����ʾ', null, 3);
commit;
prompt 25 records loaded
prompt Loading QUERYPLUS.SYS_QUERY...
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_tabstruct', 'ϵͳ���ò�ѯ-���ṹ', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_queryconf', 'ϵͳ���ò�ѯ-���ñ�', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('test1111', '����������', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_alltables', 'ϵͳ���ò�ѯ-�г����б�', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('test_dict', '��ѯ����', null, null);
insert into QUERYPLUS.SYS_QUERY (queryid, queryname, pqueryid, remark)
values ('query_dict', '��ѯ�����ֵ�', null, null);
commit;
prompt 6 records loaded
prompt Loading QUERYPLUS.SYS_QUERYFIELD...
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 20, 'ISPK', 'ISPK', '��ѯ�������', 'text', 2, 2, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICTTP', null, 'both', 2, 'DICTTP', 'DICTTP', '�ֵ������', 'text', 35, 35, null, 'plain', null, null, 'asc', '%like%', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICTTP', null, 'both', 5, 'TPNAME', 'TPNAME', '�ֵ��������', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', null, 'both', 1, 'DICID', 'DICID', '����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', 'full', 'both', 3, 'DICTTP', 'FKDICTTP', '�ֵ����', 'none', 35, 35, null, 'plain', null, null, null, '=', 'DICTTP', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', null, 'both', 4, 'KEY', 'KEY', '�ֵ�ֵ', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_dict', 'SYS_DICT', null, 'both', 6, 'VALUE', 'VALUE', '�ֵ�˵��', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 1, 'QUERYID', 'QUERYID', '��ѯ��ID', 'text', 35, 35, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_query', null, 'both', 2, 'queryname', 'queryname', '��ѯ��', 'text', 60, 60, null, 'plain', null, null, null, '%like%', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 3, 'TABNAME', 'TABNAME', '����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 4, 'JOINWAY', 'JOINWAY', '���ӷ�ʽ', 'text', 35, 35, null, 'dict', 'joinway', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 5, 'FIELDTYPE', 'FIELDTYPE', '��������', 'text', 16, 16, null, 'dict', 'fieldtype', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 6, 'FIELDORDER', 'FIELDORDER', '��������', 'text', 22, 22, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 7, 'COLREALNAME', 'COLREALNAME', '����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 8, 'COLALIAS', 'COLALIAS', '�б���', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 9, 'COLCOMMENT', 'COLCOMMENT', '��ע��', 'text', 60, 60, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 10, 'CTRLTYPE', 'CTRLTYPE', '�ؼ�����', 'text', 16, 16, null, 'dict', 'ctrltype', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 11, 'CTRLLEN', 'CTRLLEN', '���ƿ��', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 12, 'DISPLEN', 'DISPLEN', '��ʾ���', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 13, 'DISPTYPE', 'DISPTYPE', '��ʾ����', 'text', 16, 16, null, 'dict', 'disptype', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 14, 'DICTTYPE', 'DICTTYPE', '��Ӧ�ֵ������', 'text', 35, 35, null, 'dict', 'alldicttypes', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 15, 'CSS', 'CSS', 'CSS��ʽ��', 'text', 265, 265, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 16, 'ORDERBY', 'ORDERBY', '����������', 'text', 16, 16, null, 'dict', 'orderby', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 17, 'OPPER', 'OPPER', '������', 'text', 16, 16, null, 'dict', 'opper', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 18, 'OPERAND', 'OPERAND', '��������', 'text', 32, 32, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_queryfield', null, 'both', 19, 'VALIDATOR', 'VALIDATOR', '��֤', 'text', 256, 256, null, 'dict', 'validator', null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 1, 'DICID', 'DICID', '����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, '1');
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 2, 'DICTTP', 'DICTTP', '�ֵ����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 4, 'KEY', 'KEY', '�ֵ�ֵ', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 5, 'VALUE', 'VALUE', '�ֵ�˵��', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 6, 'PDICTID', 'PDICTID', '�����ڵ�', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICT', null, 'both', 7, 'SEQNO', 'SEQNO', 'ͬ������', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICTTP', null, 'both', 3, 'TPNAME', 'TPNAME', '�ֵ��������', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test1111', 'SYS_DICTTP', 'right', 'condition', 8, 'DICTTP', 'DICTTP1', '�ֵ������1', null, 35, 35, null, 'none', null, null, null, '=', 'DICTTP', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_alltables', 'all_tab_comments', null, 'condition', 1, 'owner', 'owner', null, null, null, 100, null, null, null, null, null, '=', '#QUERYPLUS', null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_alltables', 'all_tab_comments', null, 'both', 2, 'table_name', 'key', '������', 'text', 100, 100, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_alltables', 'all_tab_comments', null, 'both', 3, 'comments', 'value', '��ע��', 'text', 100, 100, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', 'full', 'both', 1, 'table_name', 'tabname', '������', 'text', 32, 32, null, 'plain', null, null, 'asc', '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_comments', null, 'output', 2, 'comments', 'tabcomment', '��ע��', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 3, 'column_id', 'colid', '��ID', null, null, 32, null, 'plain', null, null, 'asc', null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', 'full', 'both', 4, 'column_name', 'colname', '����', 'text', null, 32, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 5, 'data_type', 'datatype', '��������', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 6, 'data_length', 'datalength', '���ݳ���', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 7, 'data_precision', 'dataprecision', '��������', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_tab_columns', null, 'output', 8, 'data_scale', 'datascale', 'С������', null, null, 32, null, 'plain', null, null, null, null, null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_tabstruct', 'all_col_comments', null, 'output', 9, 'comments', 'colcnmment', '��ע��', null, null, 32, null, 'plain', null, null, null, null, null, null);
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
values ('test_dict', 'SYS_DICT', null, 'both', 2, 'DICTTP', 'DICTTP', '�ֵ����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, '1');
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 3, 'KEY', 'KEY', '�ֵ�ֵ', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 4, 'VALUE', 'VALUE', '�ֵ�˵��', 'text', 256, 256, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 5, 'PDICTID', 'PDICTID', '�����ڵ�', 'text', 35, 35, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'both', 6, 'SEQNO', 'SEQNO', 'ָ��˳��ͬgroup������Ч', 'text', 22, 22, null, 'plain', null, null, null, '=', null, null);
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('test_dict', 'SYS_DICT', null, 'condition', 1, 'DICID', 'DICID', '����', 'text', 35, 35, null, 'plain', null, null, null, '=', null, '1');
insert into QUERYPLUS.SYS_QUERYFIELD (queryid, tabname, joinway, fieldtype, fieldorder, colrealname, colalias, colcomment, ctrltype, ctrllen, displen, validator, disptype, dicttype, css, orderby, opper, operand, ispk)
values ('query_queryconf', 'sys_query', 'left', 'condition', 21, 'QUERYID', 'QUERYID1', '��ѯID', 'none', 60, 60, null, 'none', null, null, null, '=', 'QUERYID', null);
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
