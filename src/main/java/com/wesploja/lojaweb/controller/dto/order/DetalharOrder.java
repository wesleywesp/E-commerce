package com.wesploja.lojaweb.controller.dto.order;

import com.wesploja.lojaweb.doman.order.Order;
import com.wesploja.lojaweb.infra.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public record DetalharOrder(Long userId,
                            String userName,
                            String email,
                            String phone,
                            BigDecimal total,
                            String status,
                            List<DetalharOrderItemsDTO> items) {
    @Autowired
    private static EncryptionService encryptionService;

    public DetalharOrder(Order order) {
        this(order.getUser().getId(),
                order.getUser().getName(),
                order.getUser().getEmail(),
                order.getUser().getPhone(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getItems().stream().map(item -> new DetalharOrderItemsDTO(item)).toList());
    }

}
