package com.example.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.codecs.simpletext.SimpleTextCodec;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		String indexPath = "lucene-index";

		File folder = new File(indexPath);
		if(!(folder.exists() && folder.isDirectory())){
			try {
				// Memorizzo l'istante di inizio dell'indicizzazione
				long startTime = System.currentTimeMillis();
				Directory directory = FSDirectory.open(Paths.get(indexPath));

				// Configurazione dell'analyzer per ogni campo
				Map<String, Analyzer> analyzerPerField = new HashMap<>();
				analyzerPerField.put("filename", CustomAnalyzers.getFilenameAnalyzer());
				analyzerPerField.put("caption", CustomAnalyzers.getCaptionAnalyzer());
				analyzerPerField.put("column_keywords", CustomAnalyzers.getKeywordAnalyzer());
				analyzerPerField.put("row_keywords", CustomAnalyzers.getKeywordAnalyzer());

				PerFieldAnalyzerWrapper analyzerWrapper = new PerFieldAnalyzerWrapper(CustomAnalyzers.getKeywordAnalyzer(), analyzerPerField);

				IndexWriterConfig config = new IndexWriterConfig(analyzerWrapper);
				config.setCodec(new SimpleTextCodec());
				IndexWriter writer = new IndexWriter(directory, config);

				File jsonDir = new File("all_tables");
				File[] jsonFiles = jsonDir.listFiles((dir, name) -> name.endsWith(".json"));

				if (jsonFiles != null) {
					for (File jsonFile : jsonFiles) {
						try {
							// Utilizzo di TableExtractor per estrarre le tabelle nel file JSON
							List<Map<String, String>> tables = TableExtractor.extractTables(jsonFile);

							// Indicizza ciascuna tabella come documento separato
							for (Map<String, String> tableData : tables) {
								Document doc = new Document();
								doc.add(new TextField("filename", tableData.get("filename"), Field.Store.YES));
								doc.add(new TextField("score", tableData.get("score"), Field.Store.YES));
								doc.add(new TextField("id_table", tableData.get("id_table"), Field.Store.YES));
								doc.add(new TextField("table", tableData.get("table"), Field.Store.YES));
								doc.add(new TextField("caption", tableData.get("caption"), Field.Store.YES));
								doc.add(new TextField("column_keywords", tableData.get("column_keywords"), Field.Store.YES));
								doc.add(new TextField("row_keywords", tableData.get("row_keywords"), Field.Store.YES));

								writer.addDocument(doc);

								// Stampa dei dati della tabella
								System.out.println("Tabella indicizzata dal file: " + tableData.get("filename"));
								System.out.println("Score: " + tableData.get("score"));
								System.out.println("ID Tabella: " + tableData.get("id_table"));
								System.out.println("Didascalia: " + tableData.get("caption"));
								System.out.println("Parole Chiave Colonne: " + tableData.get("column_keywords"));
								System.out.println("Parole Chiave Righe: " + tableData.get("row_keywords"));
							}
						} catch (Exception e) {
							System.err.println("Errore durante l'indicizzazione del file JSON: " + jsonFile.getName());
							e.printStackTrace();
						}
					}
				}

				writer.commit();
				writer.close();
				directory.close();
				System.out.println("Indicizzazione completata!");

				// Calcolo del tempo impiegato per l'indicizzazione
				long endTime = System.currentTimeMillis();
				long elapsedTime = endTime - startTime;
				System.out.println("Tempo impiegato per l'indicizzazione: " + elapsedTime + " ms");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Avvio dell'applicazione Spring Boot
		SpringApplication.run(DemoApplication.class, args);
	}
}