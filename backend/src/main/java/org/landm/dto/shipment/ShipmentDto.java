package org.landm.dto.shipment;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShipmentDto {
        private Long id;
        private String trackingNumber;
        private String description;
        private String senderAddress;
        private String receiverAddress;
        private String receiverName;
        private BigDecimal weight;
        private String currentStatus;
        private Long userId;
        private String userFullName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

}
