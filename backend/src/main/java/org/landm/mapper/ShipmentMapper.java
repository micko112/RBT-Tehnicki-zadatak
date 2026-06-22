package org.landm.mapper;

import org.landm.dto.shipment.ShipmentDto;
import org.landm.entity.Shipment;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {

    public ShipmentDto toDto(Shipment shipment) {

        return ShipmentDto.builder()
                .id(shipment.getId())
                .trackingNumber(shipment.getTrackingNumber())
                .description(shipment.getDescription())
                .senderAddress(shipment.getSenderAddress())
                .receiverAddress(shipment.getReceiverAddress())
                .receiverName(shipment.getReceiverName())
                .weight(shipment.getWeight())
                .currentStatus(shipment.getCurrentStatus().name())
                .userId(shipment.getUser().getId())
                .userFullName(shipment.getUser().getFullName())
                .createdAt(shipment.getCreatedAt())
                .updatedAt(shipment.getUpdatedAt())
                .build();
    }
}
