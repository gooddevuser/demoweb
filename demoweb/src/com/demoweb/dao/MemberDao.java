package com.demoweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.demoweb.vo.Member;

public class MemberDao {

	public void insertMember(Member member) {
		
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
					"INSERT INTO MEMBER (memberid, passwd, email) " +
					"VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getEmail());
			
			//4. 명령 실행
			pstmt.executeUpdate(); // insert, update, delete를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
		} catch (Exception ex) {
			
		} finally {
			//6. 연결 종료
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
	}
	
	public Member selectMemberByIdAndPasswd(String memberId, String passwd) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; //조회 결과를 참조하는 변수
		
		Member member = null; // 조회 결과를 저장할 변수
		
		try {
			//1. 드라이버 등록
			Class.forName("oracle.jdbc.OracleDriver");
			
			//2. 연결 만들기
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",// 연결 정보
					"demoweb", "9922"); //계정 정보
			
			//3. SQL 작성 + 명령 만들기
			String sql = 
					"SELECT memberid, email, usertype, active, regdate " +
					"FROM MEMBER " + 
					"WHERE memberid = ? AND passwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, passwd);
			
			//4. 명령 실행
			rs = pstmt.executeQuery(); // select를 위한 메서드
			
			//5. 결과가 있다면 결과 처리 (select)
			if (rs.next()) { //조회된 데이터가 있다면
				//조회된 데이터를 Member 객체에 저장
				member = new Member();
				member.setMemberId(rs.getString(1));
				member.setEmail(rs.getString(2));
				member.setUserType(rs.getString(3));
				member.setActive(rs.getBoolean(4));
				member.setRegDate(rs.getDate(5));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//6. 연결 종료
			try { rs.close(); } catch (Exception ex) {}
			try { pstmt.close(); } catch (Exception ex) {}
			try { conn.close(); } catch (Exception ex) {}
		}
		
		return member; //조회된 데이터를 저장항 Member 객체 반환
	}
	
	public void updateMember(Member member) {
		
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
					"UPDATE MEMBER " +
					"SET PASSWD = ?, EMAIL = ? " + 
					"WHERE MEMBERID = ?";
			pstmt = conn.prepareStatement(sql);			
			pstmt.setString(1, member.getPasswd());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getMemberId());
			
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
	
}







