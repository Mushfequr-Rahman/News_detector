package com.bashundhara.mushfequr.news_detector.Model;

public class Article
{
    private Source source;

    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Source getSource() { return this.source; }

    public void setSource(Source source) { this.source = source; }

    private String author;

    public String getAuthor() { return this.author; }

    public void setAuthor(String author) { this.author = author; }

    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    private String description;

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    private String url;

    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }

    private String urlToImage;

    public String getUrlToImage() { return this.urlToImage; }

    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }

    private String publishedAt;

    public String getPublishedAt() { return this.publishedAt; }

    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }
}