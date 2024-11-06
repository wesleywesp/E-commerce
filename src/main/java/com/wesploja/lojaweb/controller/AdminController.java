package com.wesploja.lojaweb.controller;

import com.wesploja.lojaweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@PageableDefault(size = 10, sort = {"name"})Pageable pageable) {
        return adminService.getUsers(pageable);
    }
}
