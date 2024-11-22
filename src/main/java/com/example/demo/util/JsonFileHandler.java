package com.example.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JsonFileHandler {
    private static final String FILE_PATH = "queries.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public synchronized void appendQueryResult(String queryId, String query, List<Map<String, Object>> results) throws IOException {
        File file = new File(FILE_PATH);
        ObjectNode root;

        // Leggi o crea il file JSON
        if (file.exists()) {
            root = (ObjectNode) objectMapper.readTree(file);
        } else {
            root = objectMapper.createObjectNode();
        }

        // Crea un nodo per la query
        ObjectNode queryNode = objectMapper.createObjectNode();
        queryNode.put("query", query);

        // Converte i risultati in ArrayNode
        ArrayNode resultsArray = objectMapper.createArrayNode();
        for (Map<String, Object> result : results) {
            JsonNode resultNode = objectMapper.valueToTree(result);
            resultsArray.add(resultNode); // Aggiunge ogni risultato come JsonNode
        }

        queryNode.set("result", resultsArray);

        // Aggiungi la query al nodo radice
        root.set(queryId, queryNode);

        // Scrivi il file
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
    }
}