package org.landm.dto.shipment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentRequestDto {

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 255)
    private String senderAddress;

    @NotBlank
    @Size(max = 255)
    private String receiverAddress;

    @NotBlank
    @Size(max = 100)
    private String receiverName;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal weight;


}
