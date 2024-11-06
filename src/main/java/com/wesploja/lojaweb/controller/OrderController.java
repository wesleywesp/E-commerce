package com.wesploja.lojaweb.controller;

import com.wesploja.lojaweb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(@PageableDefault(sort = "id")Pageable pageable){
        return orderService.getAllOrders(pageable);
    }
//    @PostMapping("/complete")
//    @Transactional
//    public ResponseEntity<?> completeOrder(@AuthenticationPrincipal UserDetails userDetails) {
//        return orderService.completeOrder(userDetails);
//    }
}

