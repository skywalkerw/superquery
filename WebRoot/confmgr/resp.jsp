<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="wjm.query.core.ConfManager"%>
<%
	ConfManager factory = new ConfManager();
	String s = factory.ajax(request);
	//System.out.print(s);
	out.write(s);
%>