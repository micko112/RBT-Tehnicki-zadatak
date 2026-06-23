package org.landm.parser;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelShipmentParser {

    private static final int EXPECTED_COLUMNS = 6;

    public List<String[]> parse(InputStream inputStream) {

        List<String[]> rows = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                throw new RuntimeException("Excel fajl nema nijedan sheet");
            }

            Iterator<Row> rowIterator = sheet.iterator();

            if (!rowIterator.hasNext()) {
                throw new RuntimeException("Excel fajl je prazan");
            }

            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                if (isRowEmpty(row)) {
                    continue;
                }

                String[] values = new String[EXPECTED_COLUMNS];
                for (int i = 0; i < EXPECTED_COLUMNS; i++) {
                    Cell cell = row.getCell(i);
                    values[i] = (cell == null) ? "" : formatter.formatCellValue(cell).trim();
                }

                rows.add(values);
            }

        } catch (IOException e) {
            throw new RuntimeException("Greska prilikom citanja Excel fajla: " + e.getMessage());
        }

        return rows;
    }

    private boolean isRowEmpty(Row row) {

        if (row == null) {
            return true;
        }

        for (int i = 0; i < EXPECTED_COLUMNS; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != org.apache.poi.ss.usermodel.CellType.BLANK) {
                String value = cell.toString().trim();
                if (!value.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}
