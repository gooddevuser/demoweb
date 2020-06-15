package com.demoweb.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data // 모든 필드에 대해 getter, setter를 자동으로 생성하는 annotation
public class Upload {
	
	private int uploadNo;
	private String title;
	private String uploader;
	private String content;
	private int readCount;
	private Date regDate;
	private boolean deleted; // 자료의 삭제 여부 
	
	// Upload 테이블과 UploadFile 테이블 사이의 1:Many 관계를 구현한 필드
	private List<UploadFile> files;

}












