package com.niit.Bean;

public class Search {
    private String searchElement;

    public Search() {
    }

    public Search(String searchElement) {
        this.searchElement = searchElement;
    }

    public String getSearchElement() {
        return searchElement;
    }

    public void setSearchElement(String searchElement) {
        this.searchElement = searchElement;
    }

    @Override
    public String toString() {
        return "Search{" +
                "searchElement='" + searchElement + '\'' +
                '}';
    }
}
