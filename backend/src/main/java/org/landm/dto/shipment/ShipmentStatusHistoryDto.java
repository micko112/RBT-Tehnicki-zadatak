package org.landm.dto.shipment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentStatusHistoryDto {

    private Long id;
    private String status;
    private String note;
    private LocalDateTime changedAt;
}
