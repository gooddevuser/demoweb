<%@page import="com.demoweb.vo.Member"%>
<%@ page language="java" 
		 contentType="text/html; charset=utf-8"
    	 pageEncoding="utf-8"%>
    	 
<%@ taglib prefix="c" 
		   uri="http://java.sun.com/jsp/jstl/core" %>
		
		<% String bgColor = request.getParameter("bg-color"); %>
		<% if ( bgColor != null ) { %>
		<div id="header" style="background-color:<%= bgColor %>">
		<% } else { %>
		<div id="header">
		<% } %>
            <div class="title">
                <a href="/demoweb/home.action">DEMO WEBSITE</a>
            </div>
            <div class="links">
            	<% Member member = (Member)session.getAttribute("loginuser"); %>
            	<% if (member == null) { %>
            	<a href="/demoweb/account/login.action">로그인</a>
                <a href="/demoweb/account/register.action">회원가입</a>
                <% } else { %>
                <a href="/demoweb/account/update.action">
                	<%= member.getMemberId() %>
                </a>님 환영합니다.
                <a href="/demoweb/account/logout.action">로그아웃</a>
                <% } %>
            </div>
        </div>
                
        <div id="menu">
            <div>
                <ul>
                    <li><a href="/demoweb/admin/member-list.action">사용자관리</a></li>
					<li><a href="/demoweb/email/send.action">메일보내기</a></li>
					<li><a href="/demoweb/rboard/list.action">자료실</a></li>
					<li><a href="/demoweb/fboard/list.action">게시판</a></li>
                </ul>
            </div>
		</div>
    	 