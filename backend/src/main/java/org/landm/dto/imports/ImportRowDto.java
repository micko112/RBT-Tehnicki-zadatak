package org.landm.dto.imports;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportRowDto {

    private int rowNumber;

    private Long userId;

    private String description;

    private String senderAddress;

    private String receiverAddress;

    private String receiverName;

    private BigDecimal weight;
}
