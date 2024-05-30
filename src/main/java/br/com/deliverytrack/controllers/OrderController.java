package br.com.deliverytrack.controllers;

import br.com.deliverytrack.dtos.DeadlineControl;
import br.com.deliverytrack.dtos.request.OrderRequest;
import br.com.deliverytrack.dtos.response.OrderResponse;
import br.com.deliverytrack.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping("/deadline-control")
    public ResponseEntity<DeadlineControl> getDeadlineControl() {
        return ResponseEntity.ok(orderService.getDeadlineControl());
    }

    @GetMapping("/latest")
    public ResponseEntity<List<OrderResponse>> getLatestRegisteredOrders() {
        return ResponseEntity.ok(orderService.getLatestRegisteredOrders());
    }

}
