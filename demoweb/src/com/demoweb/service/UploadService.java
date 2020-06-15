package com.demoweb.service;

import java.util.List;

import com.demoweb.dao.UploadDao;
import com.demoweb.vo.Upload;
import com.demoweb.vo.UploadFile;

public class UploadService {

	private UploadDao uploadDao = new UploadDao();
	
	public void writeUpload(Upload upload) {
		
		//UploadDao 객체에게 데이터 저장 요청
		//uploadDao.insertUpload(upload);
		
		int newUploadNo = uploadDao.insertUpload(upload); //title, uploader, content, uploadno(자동생산)
		
		for (UploadFile file : upload.getFiles()) {
			file.setUploadNo(newUploadNo);
			uploadDao.insertUploadFile(file);//savedfilename, userfilename, uploadfileno(자동생산), uploadno(insertUpload에서 자동생산한 값)
		}
		
	}

	public List<Upload> findAll() {
		// UploadDao 객체에게 데이터 조회 요청
		List<Upload> uploads = uploadDao.selectUpload();
		return uploads;
	}

	public Upload findByUploadNo(int uploadNo) {
		// UploadDao 객체에게 데이터 조회 요청
		Upload upload = uploadDao.selectUploadByUploadNo(uploadNo);
		List<UploadFile> files = 
			uploadDao.selectUploadFilesByUploadNo(uploadNo);
		
		upload.setFiles(files);
		
		return upload;
	}

	public void delete(int uploadNo) {
		// UploadDao 객체에 데이터 삭제 요청
		uploadDao.deleteUpload(uploadNo);
		
	}

	public void updateUpload(Upload upload) {
		// UploadDao 객체에 데이터 삭제 요청
		uploadDao.updateUpload(upload);
		for (UploadFile file : upload.getFiles()) {
			file.setUploadNo(upload.getUploadNo());
			uploadDao.insertUploadFile(file);
		}
		
	}

	public UploadFile findUploadFileByFileNo(int fileNo) {
		// UploadDao 객체에 데이터 조회 요청
		UploadFile file = uploadDao.selectUploadFileByFileNo(fileNo);
		return file;
	}

}











