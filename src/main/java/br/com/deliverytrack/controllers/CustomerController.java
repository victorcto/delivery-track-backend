package br.com.deliverytrack.controllers;

import br.com.deliverytrack.dtos.request.CustomerRequest;
import br.com.deliverytrack.dtos.response.CustomerResponse;
import br.com.deliverytrack.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.save(customerRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(customerResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(customerResponse);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<CustomerResponse>> getFavorites() {
        return ResponseEntity.ok(customerService.getFavorites());
    }

}
