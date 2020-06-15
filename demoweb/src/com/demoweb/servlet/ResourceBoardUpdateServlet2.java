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

//@WebServlet("/rboard/update.action")
public class ResourceBoardUpdateServlet2 extends HttpServlet {

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
		
		//4. 작성자와 로그인한 사용자가 다른 경우 목록으로 이동
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("loginuser");
		if (!member.getMemberId().equals(upload.getUploader())) {
			resp.sendRedirect("list.action");
			return;
		}
		
		// 5. JSP에서 사용할 수 있도록 조회된 데이터를 request 객체에 저장
		req.setAttribute("upload", upload);
		
		// 6. JSP로 forward
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/rboard/update.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 로그인 여부 (필터에서 처리)
		
		// 2. 전송 데이터 읽기
		req.setCharacterEncoding("utf-8");
		String sUploadNo = req.getParameter("uploadNo");
		int uploadNo = -1;
		try {
			uploadNo = Integer.parseInt(sUploadNo);
		} catch (Exception e) { // 잘못된 자료번호인 경우
			resp.sendRedirect("list.action"); // 목록으로 이동
			return;
		}
		String title = req.getParameter("title");		
		String content = req.getParameter("content");
		
		// 3-1. 읽은 데이터를 Upload 객체에 저장
		Upload upload = new Upload();
		upload.setUploadNo(uploadNo);
		upload.setTitle(title);		
		upload.setContent(content);			
		
		// 3-2. 서비스 객체에 처리 요청 (이 때 Upload 객체 전달 인자로 메소드 호출)
		UploadService uploadService = new UploadService();
		uploadService.updateUpload(upload);
		
		// 4. 목록 화면으로 이동
		resp.sendRedirect("list.action");
		
		
	}

}









