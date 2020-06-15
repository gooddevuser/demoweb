package com.demoweb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home.action")
public class HomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 데이터 읽기
		// 2. 처리
		
		// 3. 응답 컨텐츠 생성
		// 3-1. JSP에서 읽을 수 있도록 데이터 저장
		// 3-2. JSP(VIEW)로 이동
		// RequestDispatche : 요청을 다른 곳으로 전달할 수 있는 객체
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/home.jsp");
		dispatcher.forward(req, resp); // forward 방식으로 반응
	}

}
