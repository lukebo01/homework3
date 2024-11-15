package com.example.demo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.KeywordRepeatFilter;
import org.apache.lucene.analysis.miscellaneous.StemmerOverrideFilter;
import org.apache.lucene.analysis.path.PathHierarchyTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.CharArraySet;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

import java.io.IOException;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CustomAnalyzers {

    public static Analyzer getCaptionAnalyzer() {
        return new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                Tokenizer tokenizer = new StandardTokenizer();
                TokenStream filter = new LowerCaseFilter(tokenizer);
                filter = new StopFilter(filter, EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
                filter = new SynonymGraphFilter(filter, buildSynonymMap(), true);
                return new TokenStreamComponents(tokenizer, filter);
            }

            // Metodo per costruire una mappa di sinonimi, inclusi quelli di WordNet
            private SynonymMap buildSynonymMap() {
                SynonymMap.Builder builder = new SynonymMap.Builder(true);
                addSynonyms(builder); // Aggiungi sinonimi specifici per il campo della computer science
                //addWordNetSynonyms(builder); // Aggiungi sinonimi generici di WordNet
                try {
                    return builder.build();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    public static Analyzer getKeywordAnalyzer() {
        return new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                Tokenizer tokenizer = new StandardTokenizer();
                TokenStream filter = new LowerCaseFilter(tokenizer);
                filter = new StopFilter(filter, EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
                filter = new SynonymGraphFilter(filter, buildSynonymMap(), true);
                filter = new PorterStemFilter(filter); // Aggiunge lo stemming per ridurre le parole alla radice
                filter = new KeywordPreserveFilter(filter, buildKeywordSet()); // Aggiungi il filtro per le parole chiave
                return new TokenStreamComponents(tokenizer, filter);
            }

            /**
             * Filtro personalizzato per mantenere le parole chiave inalterate.
             */
            private static class KeywordPreserveFilter extends TokenFilter {
                private final CharTermAttribute termAttr = addAttribute(CharTermAttribute.class);
                private final CharArraySet keywords;

                protected KeywordPreserveFilter(TokenStream input, CharArraySet keywords) {
                    super(input);
                    this.keywords = keywords;
                }

                @Override
                public final boolean incrementToken() throws IOException {
                    if (input.incrementToken()) {
                        String term = termAttr.toString();
                        if (keywords.contains(term)) {
                            // Mantieni la parola chiave inalterata
                            termAttr.setEmpty().append(term);
                        } else {
                            // Altrimenti, applica lo stemming o altre trasformazioni
                            // Qui puoi inserire ulteriore logica se necessario
                        }
                        return true;
                    }
                    return false;
                }
            }

            private CharArraySet buildKeywordSet() {
                List<String> keywords = Arrays.asList(
                        "quantum computing", "machine learning", "deep learning",
                        "neural network", "convolutional neural network", "reinforcement learning",
                        "natural language processing", "artificial intelligence", "image recognition",
                        "data mining", "big data", "cloud computing", "internet of things",
                        "blockchain", "cybersecurity", "bioinformatics", "genomics", "proteomics"
                );
                return new CharArraySet(keywords, true);
            }

            // Metodo per costruire una mappa di sinonimi, inclusi quelli di WordNet
            private SynonymMap buildSynonymMap() {
                SynonymMap.Builder builder = new SynonymMap.Builder(true);
                addSynonyms(builder); // Aggiungi sinonimi specifici per il campo della computer science
                //addWordNetSynonyms(builder); // Aggiungi sinonimi generici di WordNet
                try {
                    return builder.build();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }


    private static void addSynonyms(SynonymMap.Builder builder) {
        // Sinonimi per acronimi e termini
        builder.add(new CharsRef("convolutional"), new CharsRef("conv"), true);
        builder.add(new CharsRef("conv"), new CharsRef("convolutional"), true);

        builder.add(new CharsRef("neural network"), new CharsRef("NN"), true);
        builder.add(new CharsRef("NN"), new CharsRef("neural network"), true);

        builder.add(new CharsRef("artificial intelligence"), new CharsRef("AI"), true);
        builder.add(new CharsRef("AI"), new CharsRef("artificial intelligence"), true);

        builder.add(new CharsRef("machine learning"), new CharsRef("ML"), true);
        builder.add(new CharsRef("ML"), new CharsRef("machine learning"), true);

        builder.add(new CharsRef("deep learning"), new CharsRef("DL"), true);
        builder.add(new CharsRef("DL"), new CharsRef("deep learning"), true);

        builder.add(new CharsRef("natural language processing"), new CharsRef("NLP"), true);
        builder.add(new CharsRef("NLP"), new CharsRef("natural language processing"), true);

        builder.add(new CharsRef("reinforcement learning"), new CharsRef("RL"), true);
        builder.add(new CharsRef("RL"), new CharsRef("reinforcement learning"), true);

        builder.add(new CharsRef("computer vision"), new CharsRef("CV"), true);
        builder.add(new CharsRef("CV"), new CharsRef("computer vision"), true);

        builder.add(new CharsRef("image recognition"), new CharsRef("image classification"), true);
        builder.add(new CharsRef("image classification"), new CharsRef("image recognition"), true);

        builder.add(new CharsRef("object detection"), new CharsRef("OD"), true);
        builder.add(new CharsRef("OD"), new CharsRef("object detection"), true);

        builder.add(new CharsRef("graph neural network"), new CharsRef("GNN"), true);
        builder.add(new CharsRef("GNN"), new CharsRef("graph neural network"), true);

        builder.add(new CharsRef("generative adversarial network"), new CharsRef("GAN"), true);
        builder.add(new CharsRef("GAN"), new CharsRef("generative adversarial network"), true);

        builder.add(new CharsRef("variational autoencoder"), new CharsRef("VAE"), true);
        builder.add(new CharsRef("VAE"), new CharsRef("variational autoencoder"), true);

        builder.add(new CharsRef("recurrent neural network"), new CharsRef("RNN"), true);
        builder.add(new CharsRef("RNN"), new CharsRef("recurrent neural network"), true);

        builder.add(new CharsRef("long short-term memory"), new CharsRef("LSTM"), true);
        builder.add(new CharsRef("LSTM"), new CharsRef("long short-term memory"), true);

        builder.add(new CharsRef("gated recurrent unit"), new CharsRef("GRU"), true);
        builder.add(new CharsRef("GRU"), new CharsRef("gated recurrent unit"), true);

        builder.add(new CharsRef("transformer"), new CharsRef("TF"), true);
        builder.add(new CharsRef("TF"), new CharsRef("transformer"), true);

        builder.add(new CharsRef("bidirectional encoder representations from transformers"), new CharsRef("BERT"), true);
        builder.add(new CharsRef("BERT"), new CharsRef("bidirectional encoder representations from transformers"), true);

        builder.add(new CharsRef("generative pre-trained transformer"), new CharsRef("GPT"), true);
        builder.add(new CharsRef("GPT"), new CharsRef("generative pre-trained transformer"), true);

        builder.add(new CharsRef("attention mechanism"), new CharsRef("attention"), true);
        builder.add(new CharsRef("attention"), new CharsRef("attention mechanism"), true);

        builder.add(new CharsRef("convolutional neural network"), new CharsRef("CNN"), true);
        builder.add(new CharsRef("CNN"), new CharsRef("convolutional neural network"), true);

        builder.add(new CharsRef("autoencoder"), new CharsRef("AE"), true);
        builder.add(new CharsRef("AE"), new CharsRef("autoencoder"), true);

        builder.add(new CharsRef("support vector machine"), new CharsRef("SVM"), true);
        builder.add(new CharsRef("SVM"), new CharsRef("support vector machine"), true);

        builder.add(new CharsRef("principal component analysis"), new CharsRef("PCA"), true);
        builder.add(new CharsRef("PCA"), new CharsRef("principal component analysis"), true);

        builder.add(new CharsRef("k-nearest neighbors"), new CharsRef("KNN"), true);
        builder.add(new CharsRef("KNN"), new CharsRef("k-nearest neighbors"), true);

        builder.add(new CharsRef("random forest"), new CharsRef("RF"), true);
        builder.add(new CharsRef("RF"), new CharsRef("random forest"), true);

        builder.add(new CharsRef("gradient boosting"), new CharsRef("GB"), true);
        builder.add(new CharsRef("GB"), new CharsRef("gradient boosting"), true);

        builder.add(new CharsRef("extreme gradient boosting"), new CharsRef("XGBoost"), true);
        builder.add(new CharsRef("XGBoost"), new CharsRef("extreme gradient boosting"), true);

        builder.add(new CharsRef("natural language processing"), new CharsRef("NLP"), true);
        builder.add(new CharsRef("NLP"), new CharsRef("natural language processing"), true);

        builder.add(new CharsRef("reinforcement learning"), new CharsRef("RL"), true);
        builder.add(new CharsRef("RL"), new CharsRef("reinforcement learning"), true);

        builder.add(new CharsRef("computer vision"), new CharsRef("CV"), true);
        builder.add(new CharsRef("CV"), new CharsRef("computer vision"), true);

        builder.add(new CharsRef("image recognition"), new CharsRef("image classification"), true);
        builder.add(new CharsRef("image classification"), new CharsRef("image recognition"), true);

        builder.add(new CharsRef("object detection"), new CharsRef("OD"), true);
        builder.add(new CharsRef("OD"), new CharsRef("object detection"), true);

        builder.add(new CharsRef("graph neural network"), new CharsRef("GNN"), true);
        builder.add(new CharsRef("GNN"), new CharsRef("graph neural network"), true);

        builder.add(new CharsRef("generative adversarial network"), new CharsRef("GAN"), true);
        builder.add(new CharsRef("GAN"), new CharsRef("generative adversarial network"), true);

        builder.add(new CharsRef("variational autoencoder"), new CharsRef("VAE"), true);
        builder.add(new CharsRef("VAE"), new CharsRef("variational autoencoder"), true);
    }

    // Analyzer for name of file
    /**
     * Metodo per ottenere un analizzatore semplice per il nome del file.
     * Questo analizzatore è progettato per elaborare i nomi dei file, dove è fondamentale
     * mantenere il formato originale e richiedere una corrispondenza esatta.
     */
    public static Analyzer getFilenameAnalyzer() {
        return new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                Tokenizer tokenizer = new PathHierarchyTokenizer( '/', '\\', 256);
                return new TokenStreamComponents(tokenizer);
            }
        };
    }

}