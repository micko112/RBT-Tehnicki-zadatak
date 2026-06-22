package org.landm.helper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ShipmentNumberGenerator {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}

