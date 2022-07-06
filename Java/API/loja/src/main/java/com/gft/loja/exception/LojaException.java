package com.gft.loja.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LojaException extends RuntimeException{

	private static final long serialVersionUID = -7461723216573927617L;
	
	private String message;
	

}
