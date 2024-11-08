package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.controller.LuceneService;
import com.example.demo.model.SearchResult;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

@SpringBootTest
class DemoApplicationTests {

	private LuceneService luceneService = new LuceneService();

	// Funzione per calcolare precisione
	private double calculatePrecision(List<SearchResult> retrievedDocs, Set<String> relevantDocTitles) {
		long relevantRetrievedCount = retrievedDocs.stream()
				.filter(doc -> relevantDocTitles.contains(doc.getTitle()))
				.count();
		return (double) relevantRetrievedCount / retrievedDocs.size();
	}

	// Funzione per calcolare richiamo
	private double calculateRecall(List<SearchResult> retrievedDocs, Set<String> relevantDocTitles) {
		long relevantRetrievedCount = retrievedDocs.stream()
				.filter(doc -> relevantDocTitles.contains(doc.getTitle()))
				.count();
		return (double) relevantRetrievedCount / relevantDocTitles.size();
	}

	@Test
	public void testQueryNeuralNetworks() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Introduction to Neural Networks", "Neural Network Training Techniques"));
		List<SearchResult> results = luceneService.search("content", "neural networks");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for neural networks query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for neural networks query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQuerySupportVectorMachines() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Support Vector Machines Overview", "Advanced SVM Techniques"));
		List<SearchResult> results = luceneService.search("content", "support vector machines");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for SVM query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for SVM query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryDecisionTrees() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Decision Tree Algorithms", "Applications of Decision Trees in ML"));
		List<SearchResult> results = luceneService.search("content", "decision trees");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for decision trees query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for decision trees query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryNaturalLanguageProcessing() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Natural Language Processing with Machine Learning", "NLP Techniques in ML"));
		List<SearchResult> results = luceneService.search("content", "natural language processing");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for NLP query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for NLP query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryReinforcementLearning() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Reinforcement Learning Basics", "Applications of Reinforcement Learning"));
		List<SearchResult> results = luceneService.search("content", "reinforcement learning");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for reinforcement learning query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for reinforcement learning query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryKMeansClustering() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("K-Means Clustering Overview", "Advanced K-Means Techniques"));
		List<SearchResult> results = luceneService.search("content", "k-means clustering");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for k-means clustering query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for k-means clustering query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryPrincipalComponentAnalysis() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Principal Component Analysis Overview", "Applications of PCA in ML"));
		List<SearchResult> results = luceneService.search("content", "principal component analysis");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for PCA query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for PCA query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryDeepLearning() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Introduction to Deep Learning", "Deep Learning Models"));
		List<SearchResult> results = luceneService.search("content", "deep learning");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for deep learning query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for deep learning query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryGradientDescent() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Gradient Descent Techniques", "Applications of Gradient Descent"));
		List<SearchResult> results = luceneService.search("content", "gradient descent");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for gradient descent query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for gradient descent query. Precision: " + precision + ", Recall: " + recall);
	}

	@Test
	public void testQueryRandomForest() {
		Set<String> relevantTitles = new HashSet<>(Arrays.asList("Random Forest Models", "Random Forest for Classification"));
		List<SearchResult> results = luceneService.search("content", "random forest");

		double precision = calculatePrecision(results, relevantTitles);
		double recall = calculateRecall(results, relevantTitles);

		// Includere precisione e richiamo nel messaggio di errore
		assertTrue(precision >= 0.5,
				"Precision too low for random forest query. Precision: " + precision + ", Recall: " + recall);
		assertTrue(recall >= 0.5,
				"Recall too low for random forest query. Precision: " + precision + ", Recall: " + recall);
	}

}
