package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableExtractor {

    public static List<Map<String, String>> extractTables(File jsonFile) throws IOException {
        List<Map<String, String>> tables = new ArrayList<>();

        try (FileReader reader = new FileReader(jsonFile)) {
            JSONObject jsonObj = new JSONObject(new JSONTokener(reader));

            for (String tableId : jsonObj.keySet()) {
                if (tableId.startsWith("PAPER") || tableId.startsWith("INFO")) {
                    continue;
                }

                JSONObject tableObj = jsonObj.getJSONObject(tableId);
                Map<String, String> tableData = new HashMap<>();

                tableData.put("filename", jsonFile.getName());
                tableData.put("id_table", tableId);
                tableData.put("caption", tableObj.optString("caption", "N/A"));
                String htmlTable = tableObj.optString("table", "N/A").replaceAll("\\n", "");
                tableData.put("table", htmlTable);
                // Estrai le parole chiave dalle righe e dalle colonne
                tableData.put("column_keywords", String.join(", ", extractColumnKeywords(htmlTable)));
                tableData.put("row_keywords", String.join(", ", extractRowKeywords(htmlTable)));
                tableData.put("score", tableObj.optString("score", "N/A")); // Aggiungi se necessario

                // Solo aggiungi se tutti i campi obbligatori sono validi
                if (!tableData.get("id_table").equals("N/A")) {
                    tables.add(tableData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tables;
    }


    public static List<String> extractColumnKeywords(String htmlTable) {
        List<String> columnKeywords = new ArrayList<>();
        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(htmlTable);
        org.jsoup.nodes.Element firstRow = doc.select("tr").first();
        if (firstRow != null) {
            for (org.jsoup.nodes.Element cell : firstRow.select("th, td")) {
                columnKeywords.add(cell.text());
            }
        }
        return columnKeywords;
    }

    public static List<String> extractRowKeywords(String htmlTable) {
        List<String> rowKeywords = new ArrayList<>();
        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(htmlTable);
        for (org.jsoup.nodes.Element row : doc.select("tr")) {
            org.jsoup.nodes.Element firstCell = row.select("th, td").first();
            if (firstCell != null) {
                rowKeywords.add(firstCell.text());
            }
        }
        return rowKeywords;
    }
}


