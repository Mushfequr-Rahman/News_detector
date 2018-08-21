package com.bashundhara.mushfequr.news_detector.Model;

import java.util.ArrayList;

public class RSSObject
{
    public RSSObject(String status, Feed feed, ArrayList<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    private String status;

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    private Feed feed;

    public Feed getFeed() { return this.feed; }

    public void setFeed(Feed feed) { this.feed = feed; }

    private ArrayList<Item> items;

    public ArrayList<Item> getItems() { return this.items; }

    public void setItems(ArrayList<Item> items) { this.items = items; }
}