package com.board.domain;

import java.sql.Date;//테이블의 필드로써의 날짜자료형

import org.springframework.web.multipart.MultipartFile;

//테이블의 필드와 연관이 있는 클래스(DTO or VO)
public class BoardCommand {

	private int seq;
	private String writer,title,content,pwd;//작성자,제목,내용,암호
	private int hit;//조회수
	private Date regdate;//작성날짜
	//추가
	private MultipartFile upload;//업로드할때 필요로하는 객체
	private String filename;//업로드한 파일명
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public MultipartFile getUpload() {
		return upload;
	}
	public void setUpload(MultipartFile upload) {
		this.upload = upload;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "BoardCommand[seq="+seq+",writer="+writer+",title="
				   +title+",content="+content+",pwd="+pwd+",hit="
				   +hit+",regdate="+regdate+",upload="+upload
				   +",filename="+filename+"]";
	}
	
}
