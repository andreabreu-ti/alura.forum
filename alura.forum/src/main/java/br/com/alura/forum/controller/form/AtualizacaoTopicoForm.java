package br.com.alura.forum.controller.form;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.entity.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AtualizacaoTopicoForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String titulo;

	@NotNull
	@NotEmpty
	@Length(min = 10)
	private String mensagem;

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {

		Topico topico = topicoRepository.getOne(id);
		
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		
		return topico;
	}

}
