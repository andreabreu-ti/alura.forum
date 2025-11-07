package br.com.alura.forum.controller.form;

import br.com.alura.forum.entity.Curso;
import br.com.alura.forum.entity.Topico;
import br.com.alura.forum.repository.CursoRepository;
import lombok.Data;

@Data
public class TopicoForm {

	private String titulo;
	private String mensagem;
	private String nomeCurso;
	
	public Topico coverter(CursoRepository cursoRepository) {
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
}
