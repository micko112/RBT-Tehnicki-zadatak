package org.landm.service;

import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.dto.shipment.ShipmentStatusHistoryDto;
import org.landm.dto.shipment.UpdateShipmentStatusRequestDto;
import org.landm.entity.Shipment;

import java.util.List;

public interface ShipmentService {

    ShipmentDto createShipment(CreateShipmentRequestDto req);

    ShipmentDto getShipmentById(Long shipmentId);

    ShipmentDto getByTrackingNumber(String trackingNumber);

    List<ShipmentDto> getAll();

    ShipmentDto updateStatus(Long id, UpdateShipmentStatusRequestDto request);

    List<ShipmentStatusHistoryDto> getHistoryForShipment(Long shipmentId);

}
