package com.example.demo.controller;

import com.example.demo.model.SearchResult;
import com.example.demo.services.AsyncJsonWriterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;

@Controller
public class controller {

    @Autowired
    private AsyncJsonWriterService asyncJsonWriterService;

    private LuceneService luceneService = new LuceneService();

    @GetMapping("/")
    public String homePage(){
        return "homePage.html";
    }

    @GetMapping("/search")
    public String searchPages(Model model, @RequestParam(name = "q") String query) {
        long startTime = System.currentTimeMillis();
        System.out.println("submitted query");

        // Esegui la ricerca con il servizio Lucene
        List<SearchResult> results = luceneService.search(query);

        // Aggiungi i risultati al modello per la vista
        model.addAttribute("result", results);

        // Genera un ID unico per la query
        String queryId = "q" + System.currentTimeMillis();

        // Salva i risultati nel JSON in modo asincrono
        asyncJsonWriterService.writeResultsToJson(queryId, query, results);

        System.out.println("finished query");

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo di esecuzione: " + (endTime - startTime) + "ms");

        // Restituisci la vista al client
        return "homePage.html";
    }


}
