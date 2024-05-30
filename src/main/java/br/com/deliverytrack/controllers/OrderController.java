package br.com.deliverytrack.controllers;

import br.com.deliverytrack.dtos.request.OrderRequest;
import br.com.deliverytrack.dtos.response.OrderResponse;
import br.com.deliverytrack.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest orderDTO) {
        OrderResponse order = orderService.save(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).body(order);
    }

}
