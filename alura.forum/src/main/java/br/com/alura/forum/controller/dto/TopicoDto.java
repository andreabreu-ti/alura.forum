package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.alura.forum.entity.Topico;
import lombok.Data;

/**
 * DTO (Data Transfer Object) tem como principal objetivo transportar dados entre as camadas da aplicação 
 * — geralmente entre o backend e o frontend — sem expor diretamente as entidades do banco de dados.
 * 
 * Resumindo, o DTO server para transportar apenas o necessário para um operação específica
 * 
 * Vantagem: Evita enviar todos os dados da entidade e mantém o tráfego mais leve e seguro.
 * 
 * Objetivo: Transportar dados    | Benefício: Facilita a comunicação entre camadas
 *           Proteger entidades   |            Evita exposição de dados sensíveis
 *           Melhorar performance |            Retorna apenas os campos necessários
 *           Validar entradas	  |            Usa anotações de validação em DTOs
 *           Desacoplar camadas	  |            Segue boas práticas de arquitetura
 */

/**
 * A anotação @Data do Lombok já gera automaticamente:
 * Getters e Setters
 * toString()
 * equals() e hashCode()
 * E um construtor sem argumentos
 */

/**
 * @AllArgsConstructor Gera um construtor com todos os argumentos
 *                     automaticamente:
 */

@Data
//@AllArgsConstructor
public class TopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public TopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}

	public static Page<TopicoDto> converter(Page<Topico> topicos) {

		return topicos.map(TopicoDto::new);

	}

}
