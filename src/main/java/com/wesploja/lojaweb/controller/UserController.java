package com.wesploja.lojaweb.controller;

import com.wesploja.lojaweb.controller.dto.adress.CadastrarAddressDTO;
import com.wesploja.lojaweb.controller.dto.user.UpdatUserDTO;
import com.wesploja.lojaweb.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;




@RestController
@RequestMapping("users")
@SecurityRequirement(name = "bearer-key")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/endereco")
    @Transactional
    public ResponseEntity<?> addAddress(@RequestBody @Valid CadastrarAddressDTO dto, UriComponentsBuilder uriBuilder,
                                        @AuthenticationPrincipal UserDetails user) {
        return userService.addAdress(dto, uriBuilder, user);
    }
    @PutMapping
    @Transactional
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdatUserDTO dto , @AuthenticationPrincipal UserDetails user) {
        return userService.updateUser(dto, user);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteUser( @AuthenticationPrincipal UserDetails user) {
        return userService.deleteUser(user);
    }
    @GetMapping
    public ResponseEntity<?> getUserByAutho(@AuthenticationPrincipal UserDetails user) {
        return userService.getUserByAutho(user);
    }
}
