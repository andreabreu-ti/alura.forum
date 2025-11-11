package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.entity.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	/**
	 * Metódo para EXCLUIR tópicos
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@Transactional // Comitar as informações no banco
	public ResponseEntity<?> remover(@PathVariable Long id) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) { // Se existir um registro

			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Método para ATUALIZAR tópicos
	 * 
	 * @param id
	 * @param form
	 * @return
	 */
	@PutMapping("/{id}")
	@Transactional // Comitar as informações no banco
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) { // Se existir um registro

			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * Método para DETALHAR topicos por ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesTopicoDto> detalhar(@PathVariable Long id) {

		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) { // Se existir um registro
			return ResponseEntity.ok(new DetalhesTopicoDto(topico.get()));
		}

		return ResponseEntity.notFound().build();

	}

	/**
	 * Método para CADASTRAR tópicos
	 * 
	 * @param form
	 * @Valid Identifica que quando os dados vierem da requisição, o spring rode as
	 *        validações criada no TopicoForm
	 */
	@PostMapping
	@Transactional // Comitar as informações no banco
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {

		Topico topico = form.coverter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

		return ResponseEntity.created(uri).body(new TopicoDto(topico)); // retorna 201
	}

	/**
	 * Método para LISTAR todos os tópicos Paginação: receber todos os parametros
	 * soltos no objeto Pageable paginacao(vem informaçõe sde paginação)
	 * 
	 * @EnableSpringDataWebSupport Habilita o suporte para o Spring pegar da
	 *                             requisição, os parâmetros da url, os campos:
	 *                             paginação e ordenação, e repassar isso para o
	 *                             Spring data. Habiltar na classe main
	 * Ordenar por múltiplos campos http://localhost:8080/topicos?page=0&size=6&sort=id,asc&sort=dataCriacao,desc
	 * 
	 * @PageableDefault quando não tem o parametro de ordenação ele considera o PageableDefault como padrão
	 * 
	 * @param nomeCurso
	 * @param pagina
	 * @param qtd
	 * @param ordenacao
	 * @return
	 */
	@GetMapping
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {

		if (nomeCurso == null) {

			Page<Topico> topicos = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(topicos);

		} else {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converter(topicos);
		}
	}
}
