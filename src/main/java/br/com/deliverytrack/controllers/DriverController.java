package br.com.deliverytrack.controllers;

import br.com.deliverytrack.dtos.request.DriverRequest;
import br.com.deliverytrack.dtos.response.DriverResponse;
import br.com.deliverytrack.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
