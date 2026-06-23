package org.landm.mapper;

import org.landm.dto.imports.ImportRowDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ImportMapper {

    public ImportRowDto mapToDto(int rowNumber, String[] line) {

        if (line.length < 6) {
            throw new RuntimeException("Red nema dovoljno kolona, ocekivano 6, dobijeno " + line.length);
        }

        ImportRowDto dto = new ImportRowDto();
        dto.setRowNumber(rowNumber);
        dto.setUserId(Long.parseLong(line[0].trim()));
        dto.setDescription(line[1].trim());
        dto.setSenderAddress(line[2].trim());
        dto.setReceiverAddress(line[3].trim());
        dto.setReceiverName(line[4].trim());
        dto.setWeight(new BigDecimal(line[5].trim()));

        return dto;
    }
}
