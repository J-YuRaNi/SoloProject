package com.board.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.board.dao.BoardDao;
import com.board.domain.BoardCommand;
import com.board.util.FileUtil;
import com.board.util.StringUtil;

@Component
@Controller
public class DetailController {

	private Logger log=Logger.getLogger(getClass());//로그객체 생성구문
	
	@Autowired
	private BoardDao boardDao;
	//byType을 이용해서 BoarDao객체를 자동으로 의존주입
		
	//글상세보기
	@RequestMapping("/board/detail.do")
	public ModelAndView process(@RequestParam("seq") int seq) {
		//int seq : Integer.parseInt(request.getParameter("seq"));
		if(log.isDebugEnabled()) {
			log.debug("seq : "+seq);
		}
		//1.조회수 증가
		boardDao.updateHit(seq);
		BoardCommand board=boardDao.selectBoard(seq);
		board.setContent(StringUtil.parseBr(board.getContent()));; 
		return new ModelAndView("boardView","board",board);
	}
	
	//글상세보기 다운로드
	@RequestMapping("/board/file.do")
	public ModelAndView download(@RequestParam("filename") String filename) {
		File downloadFile=new File(FileUtil.UPLOAD_PATH+"/"+filename);
		//("다운로드받을 뷰객체","모델객체명(키명)",전달할값(다운로드 받을 파일명))
		return new ModelAndView("downloadView","donwloadFile",downloadFile);
	}
}