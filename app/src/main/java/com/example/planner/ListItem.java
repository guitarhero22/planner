package com.example.planner;

public class ListItem {
    private String Title;
    private String Date;
    private String Children;

    public ListItem(String title, String date) {
        Title = title;
        Date = date;
    }

    public String getChildren() {
        return Children;
    }

    public void setChildren(String children) {
        Children = children;
    }


    public void setTitle(String title) {
        Title = title;
    }


    public void setDate(String date) {
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

}
