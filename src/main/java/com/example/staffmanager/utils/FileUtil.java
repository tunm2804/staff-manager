package com.example.staffmanager.utils;

import com.example.staffmanager.dto.ImportExcelHistory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void saveHistoriesToFile(List<ImportExcelHistory> histories, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (ImportExcelHistory history : histories) {
                String line = String.format("%s,%s", DATE_FORMAT.format(history.getCreateDate()), history.getDescription());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle exception
        }
    }

    public static List<ImportExcelHistory> readHistoriesFromFile(String filePath) {
        List<ImportExcelHistory> histories = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try {
                        Date createDate = DATE_FORMAT.parse(parts[0]);
                        String description = parts[1];
                        histories.add(ImportExcelHistory.builder()
                                .createDate(createDate)
                                .description(description)
                                .build());
                    } catch (ParseException e) {
                        // Handle exception
                    }
                }
            }
        } catch (IOException e) {
            // Handle exception
        }
        return histories;
    }
}
