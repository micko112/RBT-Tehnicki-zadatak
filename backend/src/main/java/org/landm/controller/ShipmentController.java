package org.landm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.landm.dto.imports.ImportResultDto;
import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.dto.shipment.ShipmentStatusHistoryDto;
import org.landm.dto.shipment.UpdateShipmentStatusRequestDto;
import org.landm.service.ShipmentImportService;
import org.landm.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/shipments")
@Tag(name = "Shipments", description = "Endpointi za rad sa posiljkama")
public class ShipmentController {
    private final ShipmentService shipmentService;
    private final ShipmentImportService shipmentImportService;

    public ShipmentController(ShipmentService shipmentService, ShipmentImportService shipmentImportService) {
        this.shipmentService = shipmentService;
        this.shipmentImportService = shipmentImportService;
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
    @Operation(summary = "Menja status posiljke", description = "Validira tranziciju (CREATED → IN_TRANSIT →  " +
            "DELIVERED/CANCELLED) i belezi u history.")
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

    @PostMapping("/import")
    @Operation(summary = "Import posiljki iz CSV ili XLSX fajla",
            description = "Multipart upload. Greske se skupljaju po redu, ne prekidaju import.")
    public ResponseEntity<ImportResultDto> importShipments(@RequestParam("file") MultipartFile file) {

        ImportResultDto result = shipmentImportService.importShipments(file);

        return ResponseEntity.ok(result);
    }
}
