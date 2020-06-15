<%@page import="com.demoweb.vo.UploadFile"%>
<%@page import="com.demoweb.vo.Upload"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>자료업로드</title>
	<link rel="Stylesheet" href="/demoweb/styles/default.css" />
	<link rel="Stylesheet" href="/demoweb/styles/input2.css" />
</head>
<body>

	<div id="pageContainer">
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div style="padding-top:25px;text-align:center">
		<div id="inputcontent">
		    <div id="inputmain">
		        <div class="inputsubtitle">업로드 자료 정보</div>
		        <form action="update.action"
		        	  method="post">
		        <% Upload upload = (Upload)request.getAttribute("upload"); %>
		        <table>
		            <tr>
		                <th>제목</th>
		                <td>
		                    <input type="text" name="title" style="width:580px" 
		                    	   value="<%= upload.getTitle() %>" />
		                </td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td><%= upload.getUploader() %></td>
		            </tr>
		            <tr>
		                <th>첨부자료</th>
		                <td>
		                	<% for (UploadFile file : upload.getFiles()) { %>			                
			                <%= file.getUserFileName() %>
			                <a href="delete-file.action?fileNo=<%= file.getUploadFileNo() %>">삭제</a>
			                <br>
			                <% } %>
		                    <input type="file" name="attach" style="width:580px;height:20px" />
		                </td>
		            </tr>
		            <tr>
		                <th>자료설명</th>
		                <td>
		                	<textarea name="content" 
		                		style="width:580px" rows="15"><%= upload.getContent() %></textarea>
		                </td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	<input type="submit" value="자료등록" style="height:25px" />
		        	<input type="button" value="취소" style="height:25px"  />
		        </div>
		        </form>
		    </div>
		</div>   	
	
	</div>
	</div>

</body>
</html>