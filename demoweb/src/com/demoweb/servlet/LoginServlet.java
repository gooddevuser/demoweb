package com.demoweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demoweb.service.MemberService;
import com.demoweb.vo.Member;



@WebServlet("/account/login.action")
public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/account/login.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 데이터 읽기
		req.setCharacterEncoding("utf-8");
		String memberId = req.getParameter("memberId");
		String passwd = req.getParameter("passwd");
		// 2. 데이터 처리
		MemberService memberService = new MemberService();
		Member member = memberService.login(memberId, passwd);
		if (member != null) { // 로그인 성공
			// 로그인 처리 --> Session 객체에 데이터 저장
			HttpSession session =  req.getSession();
			session.setAttribute("loginuser", member);
			
			// 홈으로 이동
			resp.sendRedirect("/demoweb/home.action");
		} else { // 로그인 실패
			// 로그인 화면으로 다시 이동
			RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/account/login.jsp");
			dispatcher.forward(req, resp);
		}
			
		
		
		
	}
}
