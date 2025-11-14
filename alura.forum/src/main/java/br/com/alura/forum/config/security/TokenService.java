package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${alura.forum.jwt.expiration}")
	private String expiration;

	@Value("${alura.forum.jwt.secret}")
	private String secret;

	/**
	 * Inserir a API do JWT para gerar o token
	 * 
	 * O import da Classe Date é do java.util.Date;
	 * 
	 * setIssuer quem é a aplicação que esta fazendo a geração do token setSubject
	 * quem é o usuário dono desse token setIssuedAt qual foi a data de geração do
	 * token, quando ele foi concedidoi setExpiration Tempo de expiração (data)
	 * signWith o token tem que ser criptografado compact() compactar e transformar
	 * em um string
	 * 
	 * @param authentication
	 * @return
	 */
	public String gerarToken(Authentication authentication) {

		Usuario logado = (Usuario) authentication.getPrincipal();

		Date hoje = new Date();

		long expMillis = Long.parseLong(expiration);
		Date dataExpiracao = new Date(hoje.getTime() + expMillis);

		return Jwts.builder().setIssuer("API do Forum da Alura").setSubject(logado.getId().toString()).setIssuedAt(hoje)
				.setExpiration(dataExpiracao).signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}
}