package com.wesploja.lojaweb.infra.security;

import com.wesploja.lojaweb.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;


    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var tokenJwt = recuperarToken(request);
        System.out.println("Token: " + tokenJwt);
        if (tokenJwt != null) {
            var subject = tokenService.getSubject(tokenJwt);
            System.out.println("Subject: " + subject);
            var user = userRepository.findByUsername(subject);
            System.out.println("User: " + user);
            if (user.isPresent()) {
                System.out.println("User is present");
                var usuario = user.get();
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authentication: " + authentication.getAuthorities().stream().filter(a -> a.getAuthority().equals("ROLE_CLIENT")).findFirst().isPresent());
            }else {
                throw new UsernameNotFoundException("User not found");
            }
        }

        // Chama o próximo filtro na cadeia, independentemente de o token ser válido ou não
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
