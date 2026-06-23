package org.landm.service.impl;

import org.landm.dto.imports.ImportResultDto;
import org.landm.dto.imports.ImportRowDto;
import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.mapper.ImportMapper;
import org.landm.parser.CsvShipmentParser;
import org.landm.service.ShipmentImportService;
import org.landm.service.ShipmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentImportServiceImpl implements ShipmentImportService {
    private final CsvShipmentParser csvParser;
    private final ShipmentService shipmentService;
    private final ImportMapper importMapper;

    public ShipmentImportServiceImpl(CsvShipmentParser csvParser,
                                     ShipmentService shipmentService, ImportMapper importMapper) {
        this.csvParser = csvParser;
        this.shipmentService = shipmentService;
        this.importMapper = importMapper;
    }
    @Override
    public ImportResultDto importShipments(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Fajl je prazan ili nije poslat");
        }

        List<String[]> rawRows;
        try {
            rawRows = csvParser.parse(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Ne mogu da procitam fajl: " + e.getMessage());
        }

        int successCount = 0;
        int failedCount = 0;
        List<ImportResultDto.RowError> errors = new ArrayList<>();

        for (int i = 0; i < rawRows.size(); i++) {

            int rowNumber = i + 2;
            String[] line = rawRows.get(i);

            try {
                ImportRowDto dto = importMapper.mapToDto(rowNumber, line);
                CreateShipmentRequestDto request = toCreateRequest(dto);
                shipmentService.createShipment(request);
                successCount++;

            } catch (Exception e) {
                failedCount++;
                errors.add(ImportResultDto.RowError.builder()
                        .rowNumber(rowNumber)
                        .message(e.getMessage())
                        .build());
            }
        }

        return ImportResultDto.builder()
                .totalRows(rawRows.size())
                .successCount(successCount)
                .failedCount(failedCount)
                .errors(errors)
                .build();
    }

    private CreateShipmentRequestDto toCreateRequest(ImportRowDto dto) {

        CreateShipmentRequestDto request = new CreateShipmentRequestDto();
        request.setUserId(dto.getUserId());
        request.setDescription(dto.getDescription());
        request.setSenderAddress(dto.getSenderAddress());
        request.setReceiverAddress(dto.getReceiverAddress());
        request.setReceiverName(dto.getReceiverName());
        request.setWeight(dto.getWeight());

        return request;
    }
}
