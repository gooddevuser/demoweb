<%@page import="com.demoweb.vo.Upload"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>    

<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>자료 목록</title>
	<link rel="Stylesheet" href="/demoweb/styles/default.css" />
	<style>
	a { text-decoration: none }
	</style>
</head>
<body>

	<div id="pageContainer">
	
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div style="padding-top:25px;text-align:center">
			[ <a href="write.action">자료 등록</a> ]
			<br /><br />
			<table border="1" style="width:600px;margin:0 auto">
				<tr style="background-color:orange;height:30px">
					<th style="width:50px">번호</th>
					<th style="width:300px">제목</th>
					<th style="width:125px">작성자</th>
					<th style="width:125px;text-align:center">작성일</th>
				</tr>
				<% List<Upload> uploads = (List<Upload>)request.getAttribute("uploads"); %>
				<% for (Upload upload : uploads) {%>
				<tr style="height:30px">
					<td><%= upload.getUploadNo() %></td>
					<td>
						<% if ( !upload.isDeleted() ) { %>
						<a href="detail.action?uploadNo=<%= upload.getUploadNo() %>">
						<%= upload.getTitle() %>
						</a>
						<% } else { %>
						<span style="color:lightgray"><%= upload.getTitle() %> [삭제된 글]</span>
						<% } %>
					</td>
					<td><%= upload.getUploader() %></td>
					<td><%= upload.getRegDate() %></td>
				</tr>
				<% } %>
								
			</table>
			<br /><br /><br /><br />
		
		</div>
		
	</div>
		

</body>
</html>











