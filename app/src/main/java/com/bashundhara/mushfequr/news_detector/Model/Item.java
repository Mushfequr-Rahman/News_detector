package com.bashundhara.mushfequr.news_detector.Model;

import java.util.ArrayList;

public class Item
{
    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    private String pubDate;

    public String getPubDate() { return this.pubDate; }

    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    private String link;

    public String getLink() { return this.link; }

    public void setLink(String link) { this.link = link; }

    private String guid;

    public String getGuid() { return this.guid; }

    public void setGuid(String guid) { this.guid = guid; }

    private String author;

    public String getAuthor() { return this.author; }

    public void setAuthor(String author) { this.author = author; }

    private String thumbnail;

    public String getThumbnail() { return this.thumbnail; }

    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    private String description;

    public String getDescription() { return this.description; }

    public void setDescription(String description) { this.description = description; }

    private String content;

    public String getContent() { return this.content; }

    public void setContent(String content) { this.content = content; }

    private Enclosure enclosure;

    public Enclosure getEnclosure() { return this.enclosure; }

    public void setEnclosure(Enclosure enclosure) { this.enclosure = enclosure; }

    private ArrayList<Object> categories;

    public ArrayList<Object> getCategories() { return this.categories; }

    public void setCategories(ArrayList<Object> categories) { this.categories = categories; }
}