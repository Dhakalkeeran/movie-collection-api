package com.heythere.movie_info.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.heythere.movie_info.Models.Movie;
import com.heythere.movie_info.Repo.MovieRepo;

public class MovieInfoService {

    public Movie createMovieObject(HashMap<String,String> movieInfo){
        Movie movie = new Movie();
        movie.setTitle(movieInfo.get("Title"));
        movie.setId(movieInfo.get("imdbID"));
        movie.setYear(Integer.parseInt(movieInfo.get("Year")));
        movie.setDirector(movieInfo.get("Director"));
        movie.setActors(movieInfo.get("Actors"));
        movie.setPoster(movieInfo.get("Poster"));
        movie.setAllInfo(movieInfo.toString());
        return movie;
    }

    public List<Movie> searchByTitle(MovieRepo movieRepo, Map<String, String> params) {
        String title = params.get("title");
        String director = params.get("director");
        String year = params.get("year");

        if (title != null & director != null & year != null) {
            return movieRepo.findByTitleAndDirectorAndYear(title, director, Integer.parseInt(year));
        }

        else if (title != null & director != null) {
            return movieRepo.findByTitleAndDirector(title, director);
        }

        else if (title != null & year != null) {
            return movieRepo.findByTitleAndYear(title, Integer.parseInt(year));
        }

        return movieRepo.findByTitle(title);
    }

    public List<Movie> searchByDirector(MovieRepo movieRepo, Map<String, String> params) {
        String director = params.get("director");
        String year = params.get("year");

        if (director != null & year != null) {
            return movieRepo.findByDirectorAndYear(director, Integer.parseInt(year));
        }

        return movieRepo.findByDirector(director);
    }
    
}
