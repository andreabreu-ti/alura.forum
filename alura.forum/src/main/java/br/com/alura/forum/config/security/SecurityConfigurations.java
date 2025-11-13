package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	/**
     * Configura a autenticação (quem é o usuário e como validar a senha)
     */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        
		return authConfig.getAuthenticationManager();
    }
	
	/**
     * Define o serviço de autenticação e o codificador de senha (BCrypt)
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(autenticacaoService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
    
    
    
    /**
     * Configura a autorização (quais endpoints exigem login)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
    	http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/topicos").permitAll()
            .requestMatchers(HttpMethod.GET, "/topicos/*").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form.permitAll());

        return http.build();
    }
    
//    /**
//     * Configura recursos estáticos (JS, CSS, imagens)
//     * — Ocorre antes da filtragem de segurança
//     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//            .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }
    
    
//    public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
}
