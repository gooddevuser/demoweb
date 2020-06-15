package com.demoweb.vo;

import java.util.Date;

import lombok.Data;

//데이터베이스의 Member 테이블의 데이터를 저장하는 클래스
@Data // 자동으로 getter, setter, constructor, ... 을 만드는 어노테이션
public class Member {
	
	//필드 선언은 테이블의 컬럼과 일치하도록 구현
	private String memberId;
	private String passwd;
	private String email;
	private String userType;
	private Date regDate;
	private boolean active;

}
