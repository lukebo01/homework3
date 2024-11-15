package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.lucene.search.similarities.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.controller.LuceneService;
import com.example.demo.model.SearchResult;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.BooleanQuery;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class DemoApplicationTests {
}
