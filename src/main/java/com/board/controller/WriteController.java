package com.board.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger; //로그객체를 불러오겠다고 선언한 부분
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.dao.BoardDao;
import com.board.domain.BoardCommand;
import com.board.util.FileUtil;
import com.board.validator.BoardValidator;

@Controller
public class WriteController {

	//로그객체 생성문
	private Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	private BoardDao boardDao;
	
	//1.글쓰기 폼으로 이동
	@RequestMapping(value="/board/write.do",method=RequestMethod.GET)
	public String form() {
		System.out.println();
		return "boardWrite";
	}
	
	//2.에러메세지 출력
	@ModelAttribute("command")
	public BoardCommand forBacking() {
		System.out.println("forBacking()");
		return new BoardCommand();
	}
	//3.유효성검사
	@RequestMapping(value="/board/write.do",method=RequestMethod.POST)
	public String submit(@ModelAttribute("command")BoardCommand com, 
									BindingResult result) {
		if(log.isDebugEnabled()) {
			System.out.println("/board/write.do 요청(post)");
			log.debug("BoardCommand : "+com);
		}
		//유효성검사
		new BoardValidator().validate(com,result);
		//에러정보가 있다면
		if(result.hasErrors()) {
			return form();
		}
		//글쓰기 및 업로드
		try {
			String newName=""; //업로드한 파일의 변경된 파일명 저장
			//업로드 되어있다면
			if(!com.getUpload().isEmpty()) {
				newName=FileUtil.rename(com.getUpload().getOriginalFilename());
				System.out.println("newName : "+newName);
				com.setFilename(newName); //springboard2 -> filename필드명
			}
			//최대값+1
			int newSeq=boardDao.getNewSeq()+1;
			System.out.println("newSeq : "+newSeq);
			//게시물번호
			com.setSeq(newSeq);
			//글쓰기 호출
			boardDao.insert(com);
			//upload폴더로 업로드한 파일 전송(복사)
			if(!com.getUpload().isEmpty()) {
				File file=new File(FileUtil.UPLOAD_PATH+"/"+newName);
				com.getUpload().transferTo(file); //파일어볼드 위치로 전송						
			}
		}catch(IOException e) {
			e.printStackTrace();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
		return "redirect:/board/list.do";
	}
}
