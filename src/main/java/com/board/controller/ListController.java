package com.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger; //로그객체를 불러오겠다고 선언한 부분
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.board.dao.BoardDao;
import com.board.domain.BoardCommand;
import com.board.util.PagingUtil;

@Component
@Controller
public class ListController {

	//로그객체 생성문
	private Logger log=Logger.getLogger(this.getClass()); //현재 클래스명을 불러와서 지정
	
	@Autowired
	private BoardDao boardDao; //의존성 객체
	
	//1.글쓰기 폼으로 이동
	@RequestMapping("/board/list.do")
	public ModelAndView process(
		@RequestParam(value="pageNum",defaultValue="1") int currentPage, //페이지 번호
		@RequestParam(value="keyField",defaultValue="") String keyField, //검색분야
		@RequestParam(value="keyWord",defaultValue="") String keyWord //검색어
			) {
		if(log.isDebugEnabled()) { //로그 객체가 디버깅모드 상태인지 아닌지 체크
			System.out.println("/board/list.do 요청중");
			log.debug("currentPage:"+currentPage); 
			log.debug("keyField : "+keyField);
			log.debug("keyWord : "+keyWord);			
		}
		//2.Map 객체(검색분야,검색어)
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("keyField",keyField);
		map.put("keyWord",keyWord);
		//총레코드수 또는 검색된 글의 총레코드수
		int count=boardDao.getRowCount(map);
		System.out.println("ListController 클래스의 count : "+count);
		//페이징 처리
		PagingUtil page=new PagingUtil(currentPage,count,3,3,"list.do");
		//start : 첫 게시물 번호, end : 마지막 게시물 번호
		map.put("start",page.getStartCount());
		map.put("end",page.getEndCount());
		
		List<BoardCommand> list=null;
		if(count > 0) {
			list=boardDao.list(map);
		}else {
			list=Collections.EMPTY_LIST;
		}
		
		ModelAndView mav=new ModelAndView("boardList"); //boardList.jsp
		mav.addObject("count",count); //총레코드수
		mav.addObject("list",list); //검색된 레코드수
		mav.addObject("pagingHtml",page.getPagingHtml()); 
		
		return mav;		
	}	
}
