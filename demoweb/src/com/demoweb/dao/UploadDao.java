package com.demoweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.demoweb.vo.Member;
import com.demoweb.vo.Upload;
import com.demoweb.vo.UploadFile;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class UploadDao {

	public int insertUpload(Upload upload) {
		
		Connection conn = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ResultSet rs = null;
		int newUploadNo = -1;
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
				"INSERT INTO UPLOAD (uploadno, title, uploader, content) " +
				"VALUES (UPLOAD_SEQUENCE.NEXTVAL, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, upload.getTitle());
			pstmt.setString(2, upload.getUploader());
			pstmt.setString(3, upload.getContent());
			
			//4. 명령 실행
			pstmt.executeUpdate(); // insert, update, delete를 위한 메서드
			
			//********************************************
			// insert된 자료의 자동증가 자료번호 조회
			sql = "SELECT UPLOAD_SEQUENCE.CURRVAL FROM DUAL";
			pstmt2 = conn.prepareStatement(sql);
			rs = pstmt2.executeQuery();
			
			//5. 결과가 있다면 결과 처리 (select)
			rs.next();
			newUploadNo = rs.getInt(1);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { pstmt2.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return newUploadNo;
		
	}

	public List<Upload> selectUpload() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //조회 결과를 참조하는 변수
		
		// 조회 결과를 저장할 변수
		ArrayList<Upload> uploads = new ArrayList<>(); 
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
					"SELECT uploadno, title, uploader, regdate, deleted " +
					"FROM UPLOAD " +
					//"WHERE deleted = '0'" + // 삭제된 글은 조회하지 않기
					"ORDER BY uploadno DESC";
			pstmt = conn.prepareStatement(sql);
			
			//4. 명령 실행
			rs = pstmt.executeQuery(); // select를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
			while (rs.next()) { //조회된 데이터가 있다면
				//조회된 데이터를 Upload 객체에 저장
				Upload upload = new Upload();
				upload.setUploadNo(rs.getInt(1));
				upload.setTitle(rs.getString(2));
				upload.setUploader(rs.getString(3));
				upload.setRegDate(rs.getDate(4));
				upload.setDeleted(rs.getBoolean(5));
				
				//한 건의 데이터를 데이터 목록에 추가
				uploads.add(upload); 
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return uploads; //조회된 데이터 목록을 저장한 리스트 객체 반환
	}

	public Upload selectUploadByUploadNo(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //조회 결과를 참조하는 변수
		
		// 조회 결과를 저장할 변수
		Upload upload = null; 
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
					"SELECT uploadno, title, uploader, content, regdate, deleted " +
					"FROM UPLOAD " +
					"WHERE uploadno = ? AND deleted = '0'";					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);
			
			//4. 명령 실행
			rs = pstmt.executeQuery(); // select를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
			if (rs.next()) { //조회된 데이터가 있다면
				//조회된 데이터를 Upload 객체에 저장
				upload = new Upload();
				upload.setUploadNo(rs.getInt(1));
				upload.setTitle(rs.getString(2));
				upload.setUploader(rs.getString(3));
				upload.setContent(rs.getString(4));
				upload.setRegDate(rs.getDate(5));
				upload.setDeleted(rs.getBoolean(6));				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return upload; //조회된 데이터를 저장한 객체 반환
	}

	public void deleteUpload(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			//String sql = "DELETE FROM UPLOAD WHERE uploadno = ?";
			String sql = "UPDATE UPLOAD SET deleted = '1' WHERE uploadno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);
			
			//4. 명령 실행
			pstmt.executeUpdate(); // insert, update, delete를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
	}

	public void updateUpload(Upload upload) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
				"UPDATE UPLOAD " +
				"SET title = ?, content = ? " +
				"WHERE uploadno = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, upload.getTitle());			
			pstmt.setString(2, upload.getContent());
			pstmt.setInt(3, upload.getUploadNo());
			
			//4. 명령 실행
			pstmt.executeUpdate(); // insert, update, delete를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
	}

	public void insertUploadFile(UploadFile file) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
				"INSERT INTO UPLOADFILE " +
				"(uploadfileno, uploadno, userfilename, savedfilename) " +
				"VALUES (UPLOADFILE_SEQUENCE.NEXTVAL, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, file.getUploadNo());			
			pstmt.setString(2, file.getUserFileName());
			pstmt.setString(3, file.getSavedFileName());
			
			//4. 명령 실행
			pstmt.executeUpdate(); // insert, update, delete를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
	}

	public List<UploadFile> selectUploadFilesByUploadNo(int uploadNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //조회 결과를 참조하는 변수
		
		// 조회 결과를 저장할 변수
		ArrayList<UploadFile> files = new ArrayList<>(); 
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
					"SELECT uploadfileno, uploadno, userfilename, " +
					"savedfilename, downloadcount " +
					"FROM UPLOADFILE " +
					"WHERE uploadno = ?";					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, uploadNo);
			
			//4. 명령 실행
			rs = pstmt.executeQuery(); // select를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
			while (rs.next()) { //조회된 데이터가 있다면
				//조회된 데이터를 UploadFile 객체에 저장
				UploadFile file = new UploadFile();
				file.setUploadFileNo(rs.getInt(1));
				file.setUploadNo(rs.getInt(2));
				file.setUserFileName(rs.getString(3));
				file.setSavedFileName(rs.getString(4));
				file.setDownloadCount(rs.getInt(5));
				//한 건의 데이터를 목록에 추가
				files.add(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return files; //조회된 데이터를 저장한 객체 반환
	}

	public UploadFile selectUploadFileByFileNo(int fileNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //조회 결과를 참조하는 변수
		
		// 조회 결과를 저장할 변수
		UploadFile file = null; 
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
					"SELECT uploadfileno, uploadno, userfilename, " +
					"savedfilename, downloadcount " +
					"FROM UPLOADFILE " +
					"WHERE uploadfileno = ?";					
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fileNo);
			
			//4. 명령 실행
			rs = pstmt.executeQuery(); // select를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
			if (rs.next()) { //조회된 데이터가 있다면
				//조회된 데이터를 UploadFile 객체에 저장
				file = new UploadFile();
				file.setUploadFileNo(rs.getInt(1));
				file.setUploadNo(rs.getInt(2));
				file.setUserFileName(rs.getString(3));
				file.setSavedFileName(rs.getString(4));
				file.setDownloadCount(rs.getInt(5));
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return file; //조회된 데이터를 저장한 객체 반환
	}

}
















