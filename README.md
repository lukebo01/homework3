# Motore di Ricerca per Tabelle HTML

## Autori
- **Luca Borrelli, matricola 559443**
- **Rainer Cabral Ilao, matricola 560695**

## Descrizione del Progetto
Questo progetto ha come obiettivo la creazione di un motore di ricerca per tabelle HTML utilizzando Apache Lucene. Il motore di ricerca è stato implementato in Java e permette di effettuare ricerche su un insieme di tabelle HTML, indicizzando i dati contenuti nelle celle.

Il progetto è composto da due parti principali:
- **Indexer**: si occupa di indicizzare le tabelle HTML e di creare un indice invertito basato sui dati delle celle, come le parole chiave delle colonne e le righe.
- **CustomAnalyzer**: fornisce gli analizzatori per i vari campi delle tabelle HTML, come le parole chiave delle colonne e le righe.
- **Server su localhost 8080**: si occupa di effettuare le ricerche sulle tabelle HTML indicizzate, permettendo all'utente di eseguire query tramite un'interfaccia web stile motore di ricerca.

## Requisiti
- **Java 23**
- **Apache Lucene 8.11.0**
- **Apache Tomcat**
- **Jsoup**
- **Gson**
- **Servlet**
- **Javax**

## Installazione
1. Clonare il repository:
2. Importare il progetto in IntelliJ IDEA.
3. Aggiungere le librerie necessarie al progetto (Lucene, Gson, Jsoup, ecc.).
4. Importare all'interno del progetto la cartella all_tables contenente tutti i file JSON con tabelle da indicizzare.

## Utilizzo
1. Eseguire il progetto.
2. Aprire il browser e digitare http://localhost:8080/.
3. Inserire la query di ricerca per cercare all'interno delle tabelle HTML.
4. Cliccare su "Cerca".
5. Visualizzare i risultati della ricerca, che mostreranno le righe e le colonne delle tabelle che corrispondono alla query inserita.

## Dettagli Tecnici
1. Il motore di ricerca è progettato per cercare parole chiave nelle celle delle tabelle HTML.
2. Ogni ricerca restituirà le righe delle tabelle che corrispondono alla query, con la possibilità di visualizzare anche i dettagli delle celle.
3. I risultati vengono indicizzati utilizzando Lucene, che crea un indice invertito per velocizzare le ricerche.
4. L'analizzatore personalizzato (CustomAnalyzer) è stato sviluppato per ottimizzare la ricerca all'interno dei diversi campi delle tabelle, come le parole chiave delle righe e delle colonne.
5. Il progetto include funzionalità per salvare i risultati di ogni query in un file JSON, per uso futuro o per analisi.
6. Ogni volta che una query viene inviata, i risultati vengono salvati in un file JSON separato per una gestione efficiente dei dati.

