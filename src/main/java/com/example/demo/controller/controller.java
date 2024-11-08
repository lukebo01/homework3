package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class controller {
    private LuceneService luceneService = new LuceneService();

    @GetMapping("/")
    public String homePage(){
        return "homePage.html";
    }

    @GetMapping("/search")
    public String searchPages(Model model, @RequestParam(name="q") String query, @RequestParam(name="search_type") String searchType){
        //model.addAttribute("query", query);
        //model.addAttribute("searchType", searchType);
        System.out.println("submitted query");
        model.addAttribute("result", this.luceneService.search(searchType, query));
        System.out.println("finished query");
        return "homePage.html";
    }

}
