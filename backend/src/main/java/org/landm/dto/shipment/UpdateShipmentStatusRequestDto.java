package org.landm.dto.shipment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.landm.entity.enums.ShipmentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipmentStatusRequestDto {
    @NotNull
    private ShipmentStatus status;

    @Size(max = 500)
    private String note;
}
