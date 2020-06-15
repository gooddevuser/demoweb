<%@ page import="com.demoweb.vo.Upload"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
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
		        <table>
		            <tr>
		                <th>제목</th>
		                <td>${ upload.title }</td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td>${ upload.uploader }</td>
		            </tr>
		            <tr>
		            	<th>조회수</th>
		            	<td>${ upload.readCount }</td>
		            </tr>
		            <tr>
		            	<th>등록일자</th>
		            	<td>${ upload.regDate }</td>
		            </tr>
		            <tr>
		                <th>첨부자료</th>
		                <td>
		                <c:forEach var="file" items="${ upload.files }">
		                <a href="download.action?fileNo=${ file.uploadFileNo }">
		                	${ file.userFileName }
		                </a>
		                <br>
		                </c:forEach>		                
		                </td>
		            </tr>
		            <tr>
		                <th>자료설명</th>
<%-- c:set -> EL에서 사용할 수 있는 변수 만들기 --%>
<c:set var="nl" value="
"></c:set>
		                <td>${ fn:replace(upload.content, nl, "<br>") }</td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	
		        	<%-- 현재 로그인한 사용자와 작성자가 같다면 삭제, 수정 버튼 표시 --%>
		        	<c:if test="${ loginuser.memberId eq upload.uploader }">
		        	<input type="button" id="update_button" value="편집" style="height:25px" />
		        	<input type="button" id="delete_button" value="삭제" style="height:25px" />		        	
		        	</c:if>      	
		        	
		        	<input type="button" id="cancel_button" value="목록보기" style="height:25px" />
					
		        	<script type="text/javascript">
		        	
		        	</script>
		        </div>
		    </div>
		</div>   	
	
	</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#cancel_button").on('click', function(event) {
			location.href = 'list.action';
			//history.back();
		});
		
		$('#delete_button').on('click', function(event) {
			
			var answer = confirm(${ upload.uploadNo } + "번 글을 삭제할까요?");
			if (!answer) {
				return;
			}
			
			location.href = 'delete.action?uploadNo=' + ${ upload.uploadNo };
		});
		
		$('#update_button').on('click', function(event) {
			location.href = 'update.action?uploadNo=' + ${ upload.uploadNo };
		});
	});
	</script>

</body>
</html>















