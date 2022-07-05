package com.gft.loja.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String logradouro;
		private String numero;
		private String complemento;
		private String cep;

}
