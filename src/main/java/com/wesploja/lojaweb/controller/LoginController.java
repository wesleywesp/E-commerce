package com.wesploja.lojaweb.controller;

import com.wesploja.lojaweb.controller.dto.login.DadotTokenJWT;
import com.wesploja.lojaweb.controller.dto.login.LoginInputDTO;
import com.wesploja.lojaweb.controller.dto.user.CadastrarUserDTO;
import com.wesploja.lojaweb.infra.security.TokenService;
import com.wesploja.lojaweb.doman.user.User;
import com.wesploja.lojaweb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid LoginInputDTO loginInput){
        var tokenAutenticacao = new UsernamePasswordAuthenticationToken(loginInput.username(),loginInput.password());
        var authentication = manager.authenticate(tokenAutenticacao);

        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DadotTokenJWT(tokenJWT));
    }
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<?> cadastrarUser(@RequestBody @Valid CadastrarUserDTO dto, UriComponentsBuilder uriBuilder) {
        return userService.cadastrarUser(dto, uriBuilder);
    }
}
