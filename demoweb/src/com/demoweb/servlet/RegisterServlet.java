package com.demoweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demoweb.service.MemberService;
import com.demoweb.vo.Member;

@WebServlet("/account/register.action")
public class RegisterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 데이터 읽기
		// 2. 처리

		// 3. 응답 컨텐츠 생성
		// 3-1. JSP에서 읽을 수 있도록 데이터 저장
		// 3-2. JSP(VIEW)로 이동
		// RequestDispatche : 요청을 다른 곳으로 전달할 수 있는 객체
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/account/register.jsp");
		dispatcher.forward(req, resp); // forward 방식으로 반응
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 데이터 읽기
		req.setCharacterEncoding("utf-8"); // post 방식 때는 한글 처리를 위해 항상 작성하는 코드
		// req.getParameter(name) : 사용자가 보낸 데이터를 읽기 id X name O
		String memberId = req.getParameter("memberId");
		String passwd = req.getParameter("passwd");
		String email = req.getParameter("email");
		
		// 2. 처리
		// System.out.printf("[%s][%s][%s]", memberId, passwd, email);
		Member member = new Member();
		member.setMemberId(memberId);
		member.setPasswd(passwd);
		member.setEmail(email);
		
		MemberService memberService = new MemberService();
		memberService.registerMember(member);
		
		// 3. 홈으로 이동
		resp.sendRedirect("/demoweb/home.action");
		
	}

}
