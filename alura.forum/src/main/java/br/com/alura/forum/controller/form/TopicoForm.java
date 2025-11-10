package br.com.alura.forum.controller.form;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.entity.Curso;
import br.com.alura.forum.entity.Topico;
import br.com.alura.forum.repository.CursoRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TopicoForm {

	/**
	 * Colocar as anotações de validação no form
	 * 
	 * @NotNull Não pode ser nulo
	 * @NotEmpty Não pode ser vázio
	 * @Length(min = 5) define o tamnho mínimo de carcteres
	 * 
	 */
	
	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String titulo;
	
	@NotNull
	@NotEmpty
	@Length(min = 10)
	private String mensagem;
	
	@NotNull
	@NotEmpty
	private String nomeCurso;
	
	public Topico coverter(CursoRepository cursoRepository) {
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
}
