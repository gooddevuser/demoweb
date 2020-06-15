package com.demoweb.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.demoweb.common.Util;
import com.demoweb.service.UploadService;
import com.demoweb.vo.Member;
import com.demoweb.vo.Upload;
import com.demoweb.vo.UploadFile;

@WebServlet("/rboard/write.action")
public class ResourceBoardWriteServlet extends HttpServlet {

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
		
		req.setCharacterEncoding("utf-8");

		//파일 업로드를 포함한 요청인지 확인 (multipart/form-data 형식 확인)
		if (ServletFileUpload.isMultipartContent(req) == false) {
			resp.sendRedirect("list.action");
			return;
		}

		//경로 문자열을 저장할 변수
		//application.getRealPath('웹경로')
		//--> 가상경로(웹경로) -> 물리경로(컴퓨터경로)
		//--> http://..... -> C:/......
		ServletContext application = req.getServletContext();
		String path = application.getRealPath("/upload-files");		//최종 파일 저장 경로
		String tempPath = application.getRealPath("/upload-temp");	//임시 파일 저장 경로

		//전송 데이터 각 요소를 분리해서 개별 객체를 만들때 사용할 처리기
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024 * 2);	//임시 파일을 만들지 결정하는 기준 설정
		factory.setRepository(new File(tempPath));	//임시 파일 저장 경로 지정

		//요청 정보를 읽을 파서(Parser) 생성 (요청을 읽고 요소별로 분리)
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setFileSizeMax(1024 * 1024 * 10);//최대 파일 크기
		
		Upload upload = new Upload();
		ArrayList<UploadFile> files = new ArrayList<>();

		try {			
			//요청 정보를 파싱하고 개별 객체의 목록을 반환
			List<FileItem> items = uploader.parseRequest(req);
	
			////////////////////////////////////////////////////////////////////////////////
	
			//목록에 담긴 데이터 사용
			for (FileItem item : items) {
				
				if (item.isFormField()) { //form-data인 경우 (File이 아닌 일반 데이터인 경우)
					if (item.getFieldName().equals("title")) {
						upload.setTitle(item.getString("utf-8"));
					} else if (item.getFieldName().equals("uploader")) {
						upload.setUploader(item.getString("utf-8"));
					} else if (item.getFieldName().equals("content")) {
						upload.setContent(item.getString("utf-8"));
					}
				} else { //file인 경우
					String fileName = item.getName(); //파일 이름 가져오기
					if (fileName != null && fileName.length() > 0) { //내용이 있는 경우
						if (fileName.contains("\\")) { // iexplore 경우
							//C:\AAA\BBB\CCC.png -> CCC.png 
							fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
						}
						//String uniqueFileName = Util.makeUniqueFileName(path, fileName);//고유한 파일이름 만들기
						String uniqueFileName = Util.makeUniqueFileName(fileName);//고유한 파일이름 만들기
						item.write(new File(path, uniqueFileName)); //파일 저장
						item.delete(); //임시 파일 삭제
												
						UploadFile uploadFile = new UploadFile();						
						uploadFile.setUserFileName(fileName);
						uploadFile.setSavedFileName(uniqueFileName);
						
						files.add(uploadFile);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//여기까지 실행되면 1. 파일저장, 2. Upload 객체, 3. UploadFile 객체
		upload.setFiles(files);		
		
		//서비스 객체에 데이터 처리 요청
		UploadService uploadService = new UploadService();
		uploadService.writeUpload(upload);
		
		//list.action으로 이동
		resp.sendRedirect("list.action");
		
		
	}
}
