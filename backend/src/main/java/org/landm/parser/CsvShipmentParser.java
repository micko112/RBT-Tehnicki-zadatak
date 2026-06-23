package org.landm.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvShipmentParser {
    public List<String[]> parse(InputStream inputStream) {

        List<String[]> rows = new ArrayList<>();

        try (
                CSVReader reader = new CSVReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            String[] header = reader.readNext();
            if (header == null) {
                throw new RuntimeException("CSV fajl je prazan");
            }
            String[] line;
            while ((line = reader.readNext()) != null) {
                rows.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Greska prilikom citanja CSV fajla: " + e.getMessage());
        }
        return rows;
    }
}
