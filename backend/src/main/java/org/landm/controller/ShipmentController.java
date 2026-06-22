package org.landm.controller;

import jakarta.validation.Valid;
import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable Long id){

        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment(@Valid @RequestBody CreateShipmentRequestDto req) {

        return new ResponseEntity<>(shipmentService.createShipment(req), HttpStatus.CREATED);
    }


}
