package com.board.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.board.dao.BoardDao;
import com.board.domain.BoardCommand;
import com.board.util.FileUtil;
import com.board.validator.BoardDeleteValidator;

@Component
@Controller
public class DeleteController {
	
	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private BoardDao boardDao;

	//1.글삭제 폼으로 이동
	@RequestMapping(value = "/board/delete.do", method = RequestMethod.GET)
	public String form() {
		return "boardDelete";
	}
	
	//에러메세지 출력
	@ModelAttribute("command")
	public BoardCommand forBacking() {
		System.out.println("다시 암호를 재입력하기위해서 초기화함!!");
		return new BoardCommand();
	}
	
	//2.유효성검사
	@RequestMapping(value="/board/delete.do",method=RequestMethod.POST)
	public String submit(@ModelAttribute("command") BoardCommand command,
									BindingResult result) {
		if(log.isDebugEnabled()) {
			log.debug("BoardCommand : "+command);
		}
		new BoardDeleteValidator().validate(command, result);
		if(result.hasErrors()) {
			return form();
		}
		BoardCommand board=null;
		board=boardDao.selectBoard(command.getSeq());
		if(!board.getPwd().contentEquals(command.getPwd())) {
			result.rejectValue("pwd", "invalidPassword");
			return form();
		}else {
			boardDao.delete(command.getSeq());
			if(board.getFilename()!=null) {
				FileUtil.removeFile(board.getFilename());
			} //if(!command.getUpload().isEmpty())
		} //else
		return "redirect:/board/list.do";
	}
}



