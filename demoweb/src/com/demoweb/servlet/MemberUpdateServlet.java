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

@WebServlet("/account/update.action")
public class MemberUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 0. 로그인 하지 않은 사용자는 로그인 화면으로 이동
		HttpSession session = req.getSession();
		if (session.getAttribute("loginuser") == null) {
			resp.sendRedirect("login.action");
			return;
		}

		// 1. 데이터 읽기
		// 2. 처리

		// 3. 응답 컨텐츠 생성
		// JSP(VIEW)로 forward 이동
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/account/update.jsp");
		dispatcher.forward(req, resp); // forward 방식으로 반응

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 0. 로그인 하지 않은 사용자는 로그인 화면으로 이동
		HttpSession session = req.getSession();
		if (session.getAttribute("loginuser") == null) {
			resp.sendRedirect("login.action");
			return;
		}

		// 1. 데이터 읽기
		String memberId = req.getParameter("memberId");
		String passwd = req.getParameter("passwd");
		String email = req.getParameter("email");

		// 2. 처리
		Member member = new Member();
		member.setMemberId(memberId);
		member.setPasswd(passwd);
		member.setEmail(email);

		// 데이터베이스 수정
		MemberService memberService = new MemberService();
		memberService.updateMember(member);

		// Session 데이터 수정
		Member member2 = (Member) session.getAttribute("loginuser");
		member2.setEmail(member.getEmail());

		// 3. 응답 컨텐츠 생산
		// home.action으로 redirect 이동
		resp.sendRedirect("/demoweb/home.action");

	}

}
