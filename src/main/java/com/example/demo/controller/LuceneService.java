package com.example.demo.controller;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;



import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.example.demo.*;
import com.example.demo.model.*;

public class LuceneService {
    Map<String, Analyzer> analyzerPerField;
    DirectoryReader reader;
    IndexSearcher searcher;

    public LuceneService(){
        this.analyzerPerField = new HashMap<>();
        analyzerPerField.put("filename", CustomAnalyzers.getFilenameAnalyzer());
        analyzerPerField.put("caption", CustomAnalyzers.getCaptionAnalyzer());
        analyzerPerField.put("keywords", CustomAnalyzers.getKeywordAnalyzer());

        try {
            Directory indexDirectory = FSDirectory.open(Paths.get("lucene-index"));
            this.reader = DirectoryReader.open(indexDirectory);
            this.searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SearchResult> search(String queryString)
    {
        List<SearchResult> resultsList = new ArrayList<>();
        try {

            Analyzer captionAnalyzers = analyzerPerField.get("caption");
            Analyzer keywordAnalyzers = analyzerPerField.get("keywords");

            QueryParser parser1 = new QueryParser("caption", captionAnalyzers);
            Query query1 = parser1.parse(queryString);

            // Creo altre due query per cercare anche nei campi "column_keywords" e "row_keywords"
            QueryParser parser2 = new QueryParser("column_keywords", keywordAnalyzers);
            Query query2 = parser2.parse(queryString);

            QueryParser parser3 = new QueryParser("row_keywords", keywordAnalyzers);
            Query query3 = parser3.parse(queryString);

            // Creo quindi un unica grossa query per fondere i 10 migliori risultati con score più alto
            // Combina le query usando BooleanQuery
            BooleanQuery query = new BooleanQuery.Builder()
                    .add(query1, BooleanClause.Occur.SHOULD) // Cerca nel campo "caption"
                    .add(query2, BooleanClause.Occur.SHOULD) // Cerca nel campo "keywords" (es. column_keywords)
                    .add(query3, BooleanClause.Occur.SHOULD) // Cerca nel campo "keywords" (es. row_keywords)
                    .build();

            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;

            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);

                String filename = doc.get("filename");
                float score = hit.score;
                String id_table = doc.get("id_table");
                String table = doc.get("table");
                String caption = doc.get("caption");
                String column_keywords = doc.get("column_keywords");
                String row_keywords = doc.get("row_keywords");

                // Create a SearchResult object and add it to the results list
                SearchResult searchResult = new SearchResult(filename, score, id_table, table, caption, column_keywords, row_keywords);
                resultsList.add(searchResult);
            }
            return resultsList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
