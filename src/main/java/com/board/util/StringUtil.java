package com.board.util;

//게시판의 글내용을 출력할때 줄바꿈을 자동으로 인식해서 출력시켜 주기 위한 파일
public class StringUtil {
	public static String parseBr(String msg) {
		if(msg==null) return null;
		//엔터가 <br> 줄바꿈으로 인식하는 코드
		return msg.replace("\r\n", "<br>").replace("\n","<br>");
	}
}
