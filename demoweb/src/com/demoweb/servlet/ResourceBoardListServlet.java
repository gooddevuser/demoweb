package com.demoweb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demoweb.service.UploadService;
import com.demoweb.vo.Member;
import com.demoweb.vo.Upload;

@WebServlet("/rboard/list.action")
public class ResourceBoardListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 로그인 여부 확인 (로그인 하지 않은 경우 -> 로그인 화면으로 이동)
		/*
		 * HttpSession session = req.getSession(); Member member =
		 * (Member)session.getAttribute("loginuser"); if (member == null) {
		 * resp.sendRedirect("/demoweb/account/login.action"); return; }
		 */
		
		// 2. 자료실 목록 조회 (서비스 객체에 요청)
		UploadService uploadService = new UploadService();
		List<Upload> uploads = uploadService.findAll();
		
		// 3. JSP에서 사용할 수 있도록 조회된 데이터를 request 객체에 저장
		req.setAttribute("uploads", uploads);
		
		// 4. JSP로 forward
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/rboard/list.jsp");
		dispatcher.forward(req, resp);
	}

}
