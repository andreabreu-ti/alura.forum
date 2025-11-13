package br.com.alura.forum.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.alura.forum.config.security.AutenticacaoService;
import br.com.alura.forum.controller.form.LoginForm;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	private final AutenticacaoService autenticacaoService;

	AutenticacaoController(AutenticacaoService autenticacaoService) {
		this.autenticacaoService = autenticacaoService;
	}

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {

		System.out.println(form.getEmail());
		System.out.println(form.getSenha());

		return ResponseEntity.ok().build();

	}
}
