package com.heythere.movie_collection_api.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {

    @Id
    private String id;

    @Column
    private String title;

    @Column
    private int year;

    @Column
    private String actors;

    @Column
    private String director;

    @Column
    private String poster;

    @Column(columnDefinition="LONGTEXT")
    private String allInfo;

    public Movie() {
    }

    public Movie(String id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public String getActors() {
        return actors;
    }
    
    public void setActors(String actors) {
        this.actors = actors;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public String getPoster() {
        return poster;
    }
    
    public void setPoster(String poster) {
        this.poster = poster;
    }
    
    public String getAllInfo() {
        return allInfo;
    }
    
    public void setAllInfo(String allInfo) {
        this.allInfo = allInfo;
    }
    
}
