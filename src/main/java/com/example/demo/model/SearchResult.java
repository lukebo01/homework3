package com.example.demo.model;

public class SearchResult {
    private String filename;
    private float score;
    private String id_table;
    private String table;
    private String caption;
    private String column_keywords;
    private String row_keywords;

    // Constructor
    public SearchResult(String filename, float score, String id_table,String table, String caption, String column_keywords, String row_keywords) {
        this.filename = filename;
        this.score = score;
        this.table = table;
        this.id_table = id_table;
        this.caption = caption;
        this.column_keywords = column_keywords;
        this.row_keywords = row_keywords;
    }

    // Getters and Setters
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public float getScore() { return score; }
    public void setScore(float score) { this.score = score; }
    public String getId_table() { return id_table; }
    public void setId_table(String id_table) { this.id_table = id_table; }
    public String getTable() { return table; }
    public void setTable(String table) { this.table = table; }
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    public String getColumn_keywords() { return column_keywords; }
    public void setColumn_keywords(String column_keywords) { this.column_keywords = column_keywords; }
    public String getRow_keywords() { return row_keywords; }
    public void setRow_keywords(String row_keywords) { this.row_keywords = row_keywords; }

    @Override
    public String toString() {
        return "SearchResult{" +
                "filename='" + filename + '\'' +
                ", score=" + score +
                ", id_table='" + id_table + '\'' +
                ", caption='" + caption + '\'' +
                ", column_keywords='" + column_keywords + '\'' +
                ", row_keywords='" + row_keywords + '\'' +
                '}';
    }
}
