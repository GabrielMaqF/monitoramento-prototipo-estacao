package com.maqfiltros.sensors_contract.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.maqfiltros.sensors_contract.entities.Usuario;
import com.maqfiltros.sensors_contract.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private UsuarioService usuarioService;
	
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String idToken = authHeader.substring(7);

        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            
            Usuario usuario = usuarioService.findByUidFirebase(uid);
            if (usuario == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login ou Senha incorreto.");
                return;
            }
            
            // Extrai as roles personalizadas do token
            Map<String, Object> claims = decodedToken.getClaims();
            List<GrantedAuthority> authorities = new ArrayList<>();
            
            Object rolesClaim = claims.get("roles");
            if (rolesClaim instanceof List<?>) {
                ((List<?>) rolesClaim).forEach(role -> {
                    if (role instanceof String) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + ((String) role).toUpperCase()));
                    }
                });
            }
            
//            if (claims.containsKey("roles")) {
//                List<String> roles = (List<String>) claims.get("roles");
//                for (String role : roles) {
//                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
//                }
//            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    uid, null, authorities);
            authentication.setDetails(email); // Pode adicionar mais detalhes se necessário

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Token inválido
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Login ou Senha incorreto.");
            logger.warn("Falha na autenticação Firebase: {}", e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}