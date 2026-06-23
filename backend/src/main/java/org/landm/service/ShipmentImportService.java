package org.landm.service;

import org.landm.dto.imports.ImportResultDto;
import org.springframework.web.multipart.MultipartFile;

public interface ShipmentImportService {
    ImportResultDto importShipments(MultipartFile file);
}
