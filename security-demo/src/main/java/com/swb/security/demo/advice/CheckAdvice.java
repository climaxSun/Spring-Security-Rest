package com.swb.security.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * 异常处理切面
 * 
 * @author swb
 *
 */
@ControllerAdvice
public class CheckAdvice {

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<String> handleBindException(
			WebExchangeBindException e) {
		return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleBindException(
			Exception e) {
		System.out.println(e.getMessage());
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleBindException(
			MethodArgumentNotValidException e) {
		return new ResponseEntity<String>(toStr(e), HttpStatus.BAD_REQUEST);
	}
	

	/**
	 * 把校验异常转换为字符串
	 * 
	 * @param ex
	 * @return
	 */
	private String toStr(WebExchangeBindException ex) {
		return ex.getFieldErrors().stream()
				.map(e -> e.getField() + ":" + e.getDefaultMessage())
				.reduce("", (s1, s2) -> s1 + "\n" + s2);
	}

	private String toStr(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream().map(e-> e.getDefaultMessage()).reduce("",(s1,s2)->s1+"\n"+s2);
	}

}
