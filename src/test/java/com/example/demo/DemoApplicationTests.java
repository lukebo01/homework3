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

	private LuceneService luceneService = new LuceneService();

	// Metodo helper per misurare e stampare la durata del test
	private void runTestWithTiming(Runnable testMethod) {
		long startTime = System.currentTimeMillis();
		testMethod.run();
		long endTime = System.currentTimeMillis();
		System.out.println("Tempo impiegato per l'esecuzione del test: " + (endTime - startTime) + " ms\n");
	}

	@Test
	@Order(1)
	public void testIndexingAndSearchTQ() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "Efficient approximation algorithms for NP-hard problems";
				System.out.println("\n\n" + "Test 1 - Query: " + query + "\n\n");
				List<SearchResult> results = luceneService.search("title", query);
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(2)
	public void testIndexingAndSearchPQ() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "Distributed ledger technology in financial systems";
				System.out.println("\n\n" + "Test 2 - Query: " + query + "\n\n");
				List<SearchResult> results = luceneService.search("content", query);
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(3)
	public void testIndexingAndSearchPQWithSlop() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "\"Blockchain consensus mechanisms\"~2";
				System.out.println("\n\n" + "Test 3 - Query: " + query + "\n\n");
				List<SearchResult> results = luceneService.search("content", query);
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(4)
	public void testIndexingAndSearchComplexBQ() throws Exception {
		runTestWithTiming(() -> {
			try {
				Query phraseQueryTitle = new PhraseQuery("title", "Quantum", "algorithms");
				Query termQueryContent1 = new TermQuery(new Term("content", "Shor's algorithm"));
				Query termQueryContent2 = new TermQuery(new Term("content", "Grover's algorithm"));

				BooleanQuery booleanQuery = new BooleanQuery.Builder()
						.add(phraseQueryTitle, BooleanClause.Occur.MUST)
						.add(termQueryContent1, BooleanClause.Occur.SHOULD)
						.add(termQueryContent2, BooleanClause.Occur.SHOULD)
						.build();

				System.out.println("\n\n" + "Test 4 - Query: " + booleanQuery.toString() + "\n\n");
				List<SearchResult> results = luceneService.search("content", booleanQuery.toString());
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(5)
	public void testRankingWithDifferentSimilarities() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "artificial intelligence in healthcare";


				Similarity similarity = new MultiSimilarity(new Similarity[] { new ClassicSimilarity(), new BM25Similarity(),
						new BooleanSimilarity(), new LMDirichletSimilarity() });
				System.out.println("\n\n" + "Test 5 - Query: " + query + "\n\n");
				List<SearchResult> resultsMultisimilarity = luceneService.searchWithSimilarity("content", query, similarity);


				assertNotNull(resultsMultisimilarity);
				assertFalse(resultsMultisimilarity.isEmpty());


				System.out.println("\nResults using MultiSimilarity ( ClassicSimilarity, BM25Similarity, BooleanSimilarity, LMDirichletSimilarity):");
				printResults(resultsMultisimilarity);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(6)
	public void testIndexingAndSearchQP() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "\"Neural networks in natural language processing\"";
				QueryParser parser = new QueryParser("content", new StandardAnalyzer());
				Query parsedQuery = parser.parse(query);

				System.out.println("\n\n" + "Test 6 - Query: " + parsedQuery.toString() + "\n\n");
				List<SearchResult> results = luceneService.search("content", parsedQuery.toString());
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(7)
	public void testIndexingAndSearchWithSimilarity() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "quantum computing";
				Similarity similarity = new ClassicSimilarity();
				System.out.println("\n\n" + "Test 7 - Query: " + query + "\n\n");
				List<SearchResult> results = luceneService.searchWithSimilarity("content", query, similarity);
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(8)
	public void testIndexingAndSearchNegationBQ() throws Exception {
		runTestWithTiming(() -> {
			try {
				Query termQuery1 = new TermQuery(new Term("content", "dynamic programming"));
				Query termQuery2 = new TermQuery(new Term("content", "greedy algorithms"));

				BooleanQuery booleanQuery = new BooleanQuery.Builder()
						.add(termQuery1, BooleanClause.Occur.MUST)
						.add(termQuery2, BooleanClause.Occur.MUST_NOT)
						.build();

				System.out.println("\n\n" + "Test 8 - Query: " + booleanQuery.toString() + "\n\n");
				List<SearchResult> results = luceneService.search("content", booleanQuery.toString());
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(9)
	public void testIndexingAndSearchTQAbstract() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "Deep learning techniques";
				System.out.println("\n\n" + "Test 9 - Query: " + query + "\n\n");
				List<SearchResult> results = luceneService.search("abstract", query);
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Test
	@Order(10)
	public void testIndexingAndSearchTQBiblio() throws Exception {
		runTestWithTiming(() -> {
			try {
				String query = "P. Richtárik";
				System.out.println("\n\n" + "Test 10 - Query: " + query + "\n\n");
				List<SearchResult> results = luceneService.search("bibliographies", query);
				assertNotNull(results);
				assertFalse(results.isEmpty());
				printResults(results);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	// Helper method to print results in a readable format
	private void printResults(List<SearchResult> results) {
		if (results.isEmpty()) {
			System.out.println("No results found.\n");
		} else {
			System.out.println("\n\n");
			for (int i = 0; i < results.size(); i++) {
				SearchResult result = results.get(i);
				System.out.println("Result " + (i + 1) + ":");
				System.out.println("Score: " + result.getScore());
				System.out.println("Paper: " + result.getFilename());
				System.out.println("Title: " + result.getTitle());
				System.out.println("Abstract: " + result.getAbstractText());
				System.out.println("---------------------------------------");
			}
		}
	}
}
