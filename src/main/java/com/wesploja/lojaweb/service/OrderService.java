package com.wesploja.lojaweb.service;


import com.wesploja.lojaweb.controller.dto.order.DetalharOrder;
import com.wesploja.lojaweb.infra.security.EncryptionService;
import com.wesploja.lojaweb.repository.OrdemItemRepository;
import com.wesploja.lojaweb.repository.OrderRepository;
import com.wesploja.lojaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrdemItemRepository ordemItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EncryptionService encryptionService;
    public ResponseEntity<?> getOrderById(UserDetails userDetails) {
        var user = userRepository.findByUsername(userDetails.getUsername());
        var order = orderRepository.findById(user.get().getId());
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
    }



        public ResponseEntity<?>  getAllOrders(Pageable pageable) {
            var orders = orderRepository.findAll(pageable);
            return ResponseEntity.ok(orders.stream().map(order-> new DetalharOrder(order)).collect(Collectors.toList()));
    }
}
