package com.maqfiltros.sensors_contract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.maqfiltros.sensors_contract.security.ApiKeyAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final ApiKeyAuthFilter apiKeyAuthFilter;

	public SecurityConfig(ApiKeyAuthFilter apiKeyAuthFilter) {
		this.apiKeyAuthFilter = apiKeyAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						// Rotas públicas que não precisam de chave
						.requestMatchers("/sse/subscribe").permitAll()
						// Todas as outras rotas precisam de autenticação (e serão validadas pelo nosso
						// filtro)
						.anyRequest().authenticated())
				// Adiciona nosso filtro de API Key antes do filtro padrão de autenticação
				.addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
//	private final JwtAuthFilter jwtAuthFilter;
//
//	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
//		this.jwtAuthFilter = jwtAuthFilter;
//	}
//
//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable())
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authorizeHttpRequests(auth -> auth
//						// Defina aqui suas rotas públicas
//						.requestMatchers("/sse/subscribe", "/public/**").permitAll()
//						// Defina aqui as permissões para rotas específicas
//						.requestMatchers("/escolas/**").hasRole("ADMINISTRADOR").requestMatchers("/hidrometros/**")
//						.hasAnyRole("ADMINISTRADOR", "SUPERVISOR_CONTRATANTE")
//						// Todas as outras rotas precisam de autenticação
//						.anyRequest().authenticated())
//				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
}