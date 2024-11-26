package com.example.demo.controller;

import com.example.demo.model.SearchResult;
import com.example.demo.services.AsyncJsonWriterService;
import com.example.demo.services.LuceneService;
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

    @Autowired
    private LuceneService luceneService;

    @GetMapping("/")
    public String homePage(){
        return "homePage.html";
    }

    @GetMapping("/search")
    public String searchPages(Model model, @RequestParam(name = "q") String query) {
        long startTime = System.currentTimeMillis();
        System.out.println("submitted query");

        // Salva i risultati in modo asincrono
        List<SearchResult> results = luceneService.search(query);

        model.addAttribute("result", results);

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo di esecuzione: " + (endTime - startTime) + "ms");

        return "homePage.html";
    }



}
