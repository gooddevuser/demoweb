package com.demoweb.vo;

import lombok.Data;

@Data
public class UploadFile {
	
	private int uploadFileNo;
	private int uploadNo;
	private String savedFileName;
	private String userFileName;
	private int downloadCount;

}
