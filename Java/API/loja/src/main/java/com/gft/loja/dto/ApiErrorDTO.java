package com.gft.loja.dto;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDTO {

		private String message;
		private List<String> error;
		private HttpStatus status;
		
		public ApiErrorDTO(String message, String error, HttpStatus status) {
			this.message = message;
			this.error = Arrays.asList(error);
			this.status = status;
		}
}
