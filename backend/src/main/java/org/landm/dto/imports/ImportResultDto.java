package org.landm.dto.imports;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportResultDto {

    private int totalRows;

    private int successCount;

    private int failedCount;

    @Builder.Default
    private List<RowError> errors = new ArrayList<>();


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RowError {

        private int rowNumber;

        private String message;
    }
}