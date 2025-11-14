package br.com.alura.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.form.LoginForm;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {

		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			System.out.println(token);
			return ResponseEntity.ok().build();
			
		} catch (RuntimeException e) {
			
			return ResponseEntity.badRequest().build();		
		}
		
	}
}

//@RestController
//@RequestMapping("/auth")
//public class AutenticacaoController {
//	private final AutenticacaoService autenticacaoService;
//
//	AutenticacaoController(AutenticacaoService autenticacaoService) {
//		this.autenticacaoService = autenticacaoService;
//	}
//	
//	@Autowired
//	private AuthenticationManager authManager;
//
//	@PostMapping
//	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form) {
//
//		System.out.println(form.getEmail());
//		System.out.println(form.getSenha());
//
//		return ResponseEntity.ok().build();
//
//	}
//}