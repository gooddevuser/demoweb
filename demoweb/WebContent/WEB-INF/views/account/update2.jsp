<%@page import="com.demoweb.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>사용자등록</title>
	<link rel="Stylesheet" href="/demoweb/styles/default.css" />
	<link rel="Stylesheet" href="/demoweb/styles/input.css" />

</head>
<body>

	<div id="pageContainer">

		<jsp:include page="/WEB-INF/views/modules/header.jsp" />	
	
		<div id="inputcontent">
			<br /><br />
		    <div id="inputmain">
		        <div class="inputsubtitle">회원기본정보</div>

		        <form id="registerform" 
		        	  action="/demoweb/account/update.action" method="post">		        	  		        	  
		       	<%
		       	//로그인한 사용자 정보를 읽어서 변수에 저장
		       	Member member = (Member)session.getAttribute("loginuser");
		       	%>
		        <table>
		            <tr>
		                <th>아이디(ID)</th>
		                <td>
		                    <input type="text" id="memberId" name="memberId" 
		                    	   style="width:280px" readonly="readonly" value="<%= member.getMemberId() %>" />
		                </td>
		            </tr>
		            <tr>
		                <th>비밀번호</th>
		                <td>
		                	<input type="password" id="passwd" name="passwd" 
		                		   style="width:280px" />
		                </td>
		            </tr>
		            <tr>
		                <th>비밀번호 확인</th>
		                <td>
		                    <input type="password" id="confirm" name="confirm" style="width:280px" />
		                </td>
		            </tr>
		            <tr>
		                <th>이메일</th>
		                <td>
		                	<input type="text" id="email" name="email" 
		                		   style="width:280px" value="<%= member.getEmail() %>" />
		                </td>
		            </tr>
		            <tr>
		                <th>등록일자</th>
		                <td>
		                	<input type="text" id="regDate" name="regDate" 
		                		style="width:280px" readonly="readonly" value="<%= member.getRegDate() %>" />
		                </td>
		            </tr>
		            <tr>
		                <th>사용자구분</th>
		                <td>
		                	<input type="text" id="userType" name="userType" 
		                		   style="width:280px" readonly="readonly" value="<%= member.getUserType() %>" />
		                </td>
		            </tr>
		            <tr>
		                <th>활성화여부</th>
		                <td>
		                	<input type="text" id="active" name="active" 
		                		   style="width:280px" readonly="readonly" value="<%= member.isActive() %>" />
		                </td>
		            </tr>
		                       		            
		        </table>
		        <div class="buttons">
		        	<input id="update" type="submit" value="정보수정" style="height:25px" />
		        	<input id="cancel" type="button" value="취소" style="height:25px"  />

		        </div>
		        </form>
		    </div>
		</div>   	
	
	</div>
	<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
	<script type="text/javascript">
	$(function() {
		$('#cancel').on('click', function(event) {
			location.href = "/demoweb/home.action";
			//history.back();
		});
	});
	</script>

</body>
</html>














