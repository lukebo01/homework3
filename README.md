## Autori
- **Luca Borrelli, matricola 559443**
- **Rainer Cabral Ilao, matricola 560695**

## Descrizione del Progetto
Questo progetto ha come obiettivo la creazione di un motore di ricerca per documenti HTML utilizzando Apache Lucene.
Il motore di ricerca è stato implementato in Java e permette di effettuare ricerche su un insieme di documenti HTML.
Il progetto è composto da due parti principali:
- **Indexer**: si occupa di indicizzare i documenti HTML e di creare un indice invertito.
- **CustomAnaalyzer**: si occupa di fornire gli analizzatori per ogni campo del documento HTML.
- **Server su localhost 8080**: si occupa di effettuare le ricerche sui documenti HTML indicizzati, grazie all'uso di un'interfaccia stile
motore di ricerca.

## Requisiti
- **Java 23**
- **Apache Lucene 8.11.0**
- **Apache Tomcat**
- **Jsoup**
- **Gson**
- **Servlet**
- **Javax**

## Installazione
1. Clonare il repository
2. Importare il progetto in IntelliJ IDEA
3. Aggiungere le librerie necessarie al progetto
4. importare all'interno del progetto la cartella "all_htmls" contenente tutti documenti HTML da indicizzare

# NOTA: la durata dell'indicizzazione potrebbe richiedere molti minuti.

## Utilizzo
1. Eseguire il progetto
2. Aprire il browser e digitare `http://localhost:8080/`
3. Inserire la query di ricerca
4. Cliccare su "Cerca"
5. Visualizzare i risultati della ricerca
