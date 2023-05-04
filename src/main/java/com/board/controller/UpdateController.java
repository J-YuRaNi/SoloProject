package com.board.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger; //로그객체를 불러오겠다고 선언한 부분
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.board.dao.BoardDao;
import com.board.domain.BoardCommand;
import com.board.util.FileUtil;
import com.board.validator.BoardValidator;

@Component
@Controller
public class UpdateController {

	//로그객체 생성문
	//private Logger log=Logger.getLogger(WriteController.class);//로그를 처리할 클래스명
	private Logger log=Logger.getLogger(this.getClass());//현재클래스명을 불러와서 지정
	
	@Autowired
	private BoardDao boardDao;//자동적으로 Setter Methdo호출X (의존성객체 넣어줌)
	
	//1.글수정 폼으로 이동
	@RequestMapping(value="/board/update.do",method=RequestMethod.GET)
	public ModelAndView form(@RequestParam("seq") int seq) {
		System.out.println("초기화 form 호출");
		BoardCommand boardCommand=boardDao.selectBoard(seq);
		return new ModelAndView("boardModify","command",boardCommand);
	}
	
	//2.유효성 검사
	@RequestMapping(value="/board/update.do",method=RequestMethod.POST)
	public String submit(@ModelAttribute("command")BoardCommand command,
									BindingResult result) {
		if(log.isDebugEnabled()) {
			System.out.println("/board/write.do 요청");
			log.debug("BoardCommand : "+command);
		}
		new BoardValidator().validate(command,result);
		if(result.hasErrors()) {
			return "boardModify";
		}
		//변경전 데이터 불러오기
		BoardCommand board=null;
		String oldFileName="";
		board=boardDao.selectBoard(command.getSeq());
		//비밀번호 체크
		if(!board.getPwd().contentEquals(command.getPwd())) {
			result.rejectValue("pwd", "invalidPassword");
			return "boardModify";
		}else { //비밀번호가 일치한다면
			oldFileName=board.getFilename();
			//업로드 되어있다면
			if(!command.getUpload().isEmpty()) {
				try { //이미 업로드 되어있는 상태인데 새로운 파일로 업로드 하는 경우
					command.setFilename(FileUtil.rename(command.getUpload().getOriginalFilename()));
				}catch(Exception e) {
					e.printStackTrace();
				}
			}else { //새로운 파일로 업로드 하지 않는 경우
				command.setFilename(oldFileName); //기존 업로드된 파일 그대로 다시 저장
			}
			//자료실 글수정
			 boardDao.update(command);
			 //실제로 upload 폴더로 업로드한 파일을 전송
			 	if(!command.getUpload().isEmpty()) {
			 		try {
			 			File file=new File(FileUtil.UPLOAD_PATH+"/"+command.getFilename());
			 			command.getUpload().transferTo(file); //파일 업로드 위치로 전송
			 		}catch(IOException e) {
			 			e.printStackTrace();
			 		}catch(Exception e2) {
			 			e2.printStackTrace();
			 		}
			 		if(oldFileName!=null) {
			 			FileUtil.removeFile(oldFileName);
			 		} //if(!command.getUpload().isEmpty()
			 	} //else
			 	return "redirect:/board/list.do";
		}
	}
}
