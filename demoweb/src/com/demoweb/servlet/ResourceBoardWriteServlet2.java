package com.demoweb.servlet;

import java.io.IOException;

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

//@WebServlet("/rboard/write.action")
public class ResourceBoardWriteServlet2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 로그인 여부 확인 (로그인 하지 않은 경우 -> 로그인 화면으로 이동)
		//    -> 필터에서 처리
		
		// 2. JSP로 forward
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/rboard/write.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 로그인 여부 (필터에서 처리)
		
		// 2. 전송 데이터 읽기
		req.setCharacterEncoding("utf-8");
		String title = req.getParameter("title");
		String uploader = req.getParameter("uploader");
		String content = req.getParameter("content");
		
		// 3-1. 읽은 데이터를 Upload 객체에 저장
		Upload upload = new Upload();
		upload.setTitle(title);
		upload.setUploader(uploader);
		upload.setContent(content);
		
		// 3-2. 서비스 객체에 처리 요청 (이 때 Upload 객체 전달 인자로 메소드 호출)
		UploadService uploadService = new UploadService();
		uploadService.writeUpload(upload);
		
		// 4. 목록 화면으로 이동
		resp.sendRedirect("list.action");
		
		
	}
}
