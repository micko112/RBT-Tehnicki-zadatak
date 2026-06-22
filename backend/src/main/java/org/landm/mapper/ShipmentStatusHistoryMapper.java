package org.landm.mapper;

import org.landm.dto.shipment.ShipmentStatusHistoryDto;
import org.landm.entity.ShipmentStatusHistory;
import org.springframework.stereotype.Component;

@Component
public class ShipmentStatusHistoryMapper {

    public ShipmentStatusHistoryDto toDto(ShipmentStatusHistory entry) {

        return ShipmentStatusHistoryDto.builder()
                .id(entry.getId())
                .status(entry.getStatus().name())
                .note(entry.getNote())
                .changedAt(entry.getChangedAt())
                .build();
    }
}
