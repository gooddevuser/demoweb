<%@page import="com.demoweb.vo.UploadFile"%>
<%@ page import="com.demoweb.vo.Member"%>
<%@ page import="com.demoweb.vo.Upload"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>자료업로드</title>
	<link rel="Stylesheet" href="/demoweb/styles/default.css" />
	<link rel="Stylesheet" href="/demoweb/styles/input.css" />
</head>
<body>

	<div id="pageContainer">
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div style="padding-top:25px;text-align:center">
		<div id="inputcontent">
		    <div id="inputmain">
		        <div class="inputsubtitle">업로드 자료 정보</div>
		        <% Upload upload = (Upload)request.getAttribute("upload"); %>
		        <% Member member = (Member)session.getAttribute("loginuser"); %>
		        <table>
		            <tr>
		                <th>제목</th>
		                <td><%= upload.getTitle() %></td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td><%= upload.getUploader() %></td>
		            </tr>
		            <tr>
		            	<th>조회수</th>
		            	<td><%= upload.getReadCount() %></td>
		            </tr>
		            <tr>
		            	<th>등록일자</th>
		            	<td><%= upload.getRegDate() %></td>
		            </tr>
		            <tr>
		                <th>첨부자료</th>
		                <td>
		                <% for (UploadFile file : upload.getFiles()) { %>
		                <a href="download.action?fileNo=<%= file.getUploadFileNo() %>">
		                <%= file.getUserFileName() %>
		                </a>
		                <br>
		                <% } %>		                
		                </td>
		            </tr>
		            <tr>
		                <th>자료설명</th>
		                <td><%= upload.getContent().replace("\r\n", "<br>") %></td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	<%-- 현재 로그인한 사용자와 작성자가 같다면 삭제, 수정 버튼 표시 --%>
		        	<% if (member.getMemberId().equals(upload.getUploader())) { %>
		        	<input type="button" id="update_button" value="편집" style="height:25px" />
		        	<input type="button" id="delete_button" value="삭제" style="height:25px" />		        	
		        	<% } %>
		        	
		        	<input type="button" id="cancel_button" value="목록보기" style="height:25px" />
					
		        	<script type="text/javascript">
		        	
		        	</script>
		        </div>
		    </div>
		</div>   	
	
	</div>
	</div>

</body>
</html>