package com.board.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

//다운로드를 따로 처리해줄 수 있는 전용뷰 클래스
public class DownloadView extends AbstractView {

	public DownloadView() {
		//다운로드 받는 화면으로 자동으로 전환
		setContentType("application/download");//text/html에서 변경
	}
	//모델값(화면에 출력할 대상자)을 매개변수로 전달받아서 처리해주는 메서드
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
     //return new ModelAndView("downloadView","downloadFile",downloadFile);
		//1.다운로드 받을 파일의 정보 얻어오기
		File file=(File)model.get("downloadFile");//Object->(File)
		System.out.println("file=>"+file);
		//2.다운받을 파일의 타입,크기를 지정
		response.setContentType(getContentType());//"application/download"
		response.setContentLength((int)file.length());//다운로드 받을 파일의 길이 설정
		//브라우저별로 한글처리->User-Agent(브라우저 정보가 저장된 매개변수명(head값)
		String userAgent=request.getHeader("User-Agent");
		System.out.println("userAgent=>"+userAgent);//MSIE
		boolean ie=userAgent.indexOf("MSIE") > -1; //못찾으면 -1 리턴
		String fileName=null;
		if(ie) {
			fileName=URLEncoder.encode(file.getName(),"utf-8");//utf-8로 설정->한글 O
		}else {//영문
			fileName=new String(file.getName().getBytes("utf-8"),"iso8859-1");//영문 O
		}
		//대화상자에서 원하는 위치를 다운로드 대화상자에서 지정
		//Content-Disposition=>다운로드 받는 위치,"attachment;filename=다운로드 받을파일명
		//exe,bat=>이진파일도 다운=>Content-Transfer-Encoding을 binary로 지정
		response.setHeader("Content-Disposition",
				        "attachment;filename=\""+fileName+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		//입출력객체를 만들어서 전송
		OutputStream out=response.getOutputStream();
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(file);//다운로드받을 파일정보
			//서버로부터 파일을 읽여들여서 다운로드 받음->복사
			FileCopyUtils.copy(fis,out);//1.다운로드 받는쪽 입력객체명 2.서버의 출력객체명
		}catch(IOException e) {
			e.printStackTrace();
		}finally {//예외처리와 상관없이 항상 처리해야할 구문->메모리 해제
			if(fis!=null)
				try {fis.close();}catch(IOException e) {}
		}
		out.flush();//입출력->양이 될때까지 그대로 버퍼에 보관X ->flush()->바로 출력
	}
}




