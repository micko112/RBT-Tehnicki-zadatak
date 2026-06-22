package org.landm.controller;

import jakarta.validation.Valid;
import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.dto.shipment.ShipmentStatusHistoryDto;
import org.landm.dto.shipment.UpdateShipmentStatusRequestDto;
import org.landm.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<ShipmentDto>> getAll() {

        List<ShipmentDto> shipments = shipmentService.getAll();

        return ResponseEntity.ok(shipments);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShipmentDto> createShipment(@Valid @RequestBody CreateShipmentRequestDto req) {

        return new ResponseEntity<>(shipmentService.createShipment(req), HttpStatus.CREATED);
    }
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ShipmentDto> getByTrackingNumber(@PathVariable String trackingNumber) {

        ShipmentDto shipment = shipmentService.getByTrackingNumber(trackingNumber);

        return ResponseEntity.ok(shipment);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<ShipmentDto> updateStatus(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateShipmentStatusRequestDto request) {

        ShipmentDto updated = shipmentService.updateStatus(id, request);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ShipmentStatusHistoryDto>> getHistory(@PathVariable Long id) {

        List<ShipmentStatusHistoryDto> history = shipmentService.getHistoryForShipment(id);

        return ResponseEntity.ok(history);
    }
}
