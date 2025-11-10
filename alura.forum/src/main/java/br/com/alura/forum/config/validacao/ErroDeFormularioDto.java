package br.com.alura.forum.config.validacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErroDeFormularioDto {

	private String campo;
	private String erro;

}
