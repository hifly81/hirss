package org.hifly.hirss.model;

import java.util.List;

public class Rss {

    protected String title;
    protected String link;
    protected String description;
    protected List<Item> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return title + " - " + link + " - " + description + " - " + items==null? "no items" : items.size() + " items";
    }
}
