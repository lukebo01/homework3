package com.example.demo.services;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.example.demo.util.JsonFileHandler;
import com.example.demo.model.SearchResult;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

@Service
public class AsyncJsonWriterService {
    private final JsonFileHandler jsonFileHandler; // Usa la classe per gestire il file JSON

    public AsyncJsonWriterService(JsonFileHandler jsonFileHandler) {
        this.jsonFileHandler = jsonFileHandler;
    }

    @Async
    public void writeResultsToJson(String queryId, String query, List<SearchResult> results, long time) {
        try {
            // Converti i risultati in un formato adatto al JSON
            List<Map<String, Object>> formattedResults = results.stream().map(result -> {
                Map<String, Object> map = new HashMap<>();
                map.put("pos", results.indexOf(result) + 1);
                map.put("filename", result.getFilename());
                map.put("id_table", result.getId_table());
                return map;
            }).toList();

            // Scrivi i risultati nel file JSON
            jsonFileHandler.appendQueryResult(queryId, query, formattedResults, time);
            System.out.println("Risultati salvati nel file JSON per la query: " + query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
