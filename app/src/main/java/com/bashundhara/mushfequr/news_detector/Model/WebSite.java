package com.bashundhara.mushfequr.news_detector.Model;

import java.util.ArrayList;

public class WebSite
{
    public WebSite(String status, int totalResults, ArrayList<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    private String status;

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    private int totalResults;

    public int getTotalResults() { return this.totalResults; }

    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }

    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles() { return this.articles; }

    public void setArticles(ArrayList<Article> articles) { this.articles = articles; }
}