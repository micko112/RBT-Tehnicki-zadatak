package org.landm.service;

import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.entity.Shipment;

import java.util.List;

public interface ShipmentService {

    ShipmentDto createShipment(CreateShipmentRequestDto req);

    ShipmentDto getShipmentById(Long shipmentId);

    List<ShipmentDto> getAll();
}
