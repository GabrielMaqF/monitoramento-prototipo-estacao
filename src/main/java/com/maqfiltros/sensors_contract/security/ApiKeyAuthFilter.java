package com.maqfiltros.sensors_contract.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.maqfiltros.sensors_contract.config.ApiGenericConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

//	@Value("${api.security.key}")
	@Autowired
	private ApiGenericConfig token;

	private static final String API_KEY_HEADER = "X-API-KEY";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestApiKey = request.getHeader(API_KEY_HEADER);

		if (token.getKey().equals(requestApiKey)) {
			// Chave válida, concede permissão
			var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_API_USER"));
			var authentication = new UsernamePasswordAuthenticationToken("api-user", null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}