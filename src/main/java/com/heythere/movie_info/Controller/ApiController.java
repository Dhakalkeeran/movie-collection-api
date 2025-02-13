package com.heythere.movie_info.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.heythere.movie_info.Models.Movie;
import com.heythere.movie_info.Repo.MovieRepo;

@RestController
@RequestMapping(value = "/")
public class ApiController {

    @Autowired
    private MovieRepo movieRepo;

    private final String OMDB_API_KEY = System.getenv("OMDB_API_KEY");
    private String url = "https://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&";

    @GetMapping(value = "add/")
    public String getMovieInfo(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String id) {

        if (id != null | title != null) {
            RestTemplate restTemplate = new RestTemplate();
            HashMap<String, String> movieInfo;
            if (id != null) {
                @SuppressWarnings("unchecked")
                HashMap<String, String> response = restTemplate.getForObject(url+"i="+id, HashMap.class);
                movieInfo = response;
            }
            else {
                @SuppressWarnings("unchecked")
                HashMap<String, String> response = restTemplate.getForObject(url+"t="+title, HashMap.class);
                movieInfo = response;
            }
            
            Movie movie = new Movie();
            movie.setTitle(movieInfo.get("Title"));
            movie.setId(movieInfo.get("imdbID"));
            movie.setYear(Integer.parseInt(movieInfo.get("Year")));
            movie.setDirector(movieInfo.get("Director"));
            movie.setActors(movieInfo.get("Actors"));
            movie.setPoster(movieInfo.get("Poster"));
            movie.setAllInfo(movieInfo.toString());

            movieRepo.save(movie);

            return movie.getTitle() + ", " + movie.getYear() + ", Directed by " + movie.getDirector() + ", Actors: " + movie.getActors();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

}
