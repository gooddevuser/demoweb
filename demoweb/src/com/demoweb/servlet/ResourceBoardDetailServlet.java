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

@WebServlet("/rboard/detail.action")
public class ResourceBoardDetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 로그인 여부 확인 (로그인 하지 않은 경우 -> 로그인 화면으로 이동)
		/*
		 * HttpSession session = req.getSession(); Member member =
		 * (Member)session.getAttribute("loginuser"); if (member == null) {
		 * resp.sendRedirect("/demoweb/account/login.action"); return; }
		 */
		// 2. 사용자가 선택한 자료 번호 읽기
		String sUploadNo = req.getParameter("uploadNo");
		int uploadNo = -1;
		try {
			uploadNo = Integer.parseInt(sUploadNo);
		} catch (Exception e) { // 잘못된 자료번호인 경우
			resp.sendRedirect("list.action"); // 목록으로 이동
			return;
		}
		
		// 3. 사용자가 선택한 자료 정보 조회 (서비스 객체에 요청)
		UploadService uploadService = new UploadService();
		Upload upload = uploadService.findByUploadNo(uploadNo);		
		
		if (upload == null) { // 조회 실패
			resp.sendRedirect("list.action");
			return;
		}
		
		// 4. JSP에서 사용할 수 있도록 조회된 데이터를 request 객체에 저장
		req.setAttribute("upload", upload);
		
		// 5. JSP로 forward
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/rboard/detail.jsp");
		dispatcher.forward(req, resp);
	}

}









