package br.com.deliverytrack.controllers;

import br.com.deliverytrack.dtos.request.DriverRequest;
import br.com.deliverytrack.dtos.response.DriverResponse;
import br.com.deliverytrack.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverResponse> create(@RequestBody DriverRequest driverRequest) {
        DriverResponse driverResponse = driverService.save(driverRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(driverResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(driverResponse);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAll() {
        return ResponseEntity.ok(driverService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getById(id));
    }
}
