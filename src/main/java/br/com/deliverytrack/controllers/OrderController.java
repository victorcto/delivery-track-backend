package br.com.deliverytrack.controllers;

import br.com.deliverytrack.dtos.OrderDTO;
import br.com.deliverytrack.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(final OrderDTO orderDTO) {
        OrderDTO order = orderService.save(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(order);
    }



}
