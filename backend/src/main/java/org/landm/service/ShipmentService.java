package org.landm.service;

import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.dto.shipment.ShipmentStatusHistoryDto;
import org.landm.dto.shipment.UpdateShipmentStatusRequestDto;
import org.landm.entity.Shipment;
import org.landm.entity.enums.ShipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ShipmentService {

    ShipmentDto createShipment(CreateShipmentRequestDto req);

    ShipmentDto getShipmentById(Long shipmentId);

    ShipmentDto getByTrackingNumber(String trackingNumber);

    Page<ShipmentDto> getAll(Long userId,
                             ShipmentStatus status,
                             LocalDateTime dateFrom,
                             LocalDateTime dateTo,
                             Pageable pageable);

    ShipmentDto updateStatus(Long id, UpdateShipmentStatusRequestDto request);

    List<ShipmentStatusHistoryDto> getHistoryForShipment(Long shipmentId);

}
