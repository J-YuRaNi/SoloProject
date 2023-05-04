package com.board.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.board.domain.BoardCommand;

//유효성 검사
public class BoardValidator implements Validator {

	//1.유효성 검사를 할 클래스명을 지정해주는 메서드
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return BoardCommand.class.isAssignableFrom(clazz);
	}
	//2.유효성 검사를 해주는 메서드
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		//1.에러객체명 2.적용시킬 필드명 3.적용시킬 에러코드명
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "required");
	}
}