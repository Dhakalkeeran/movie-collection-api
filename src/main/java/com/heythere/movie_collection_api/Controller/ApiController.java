package com.heythere.movie_collection_api.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.heythere.movie_collection_api.Models.Movie;
import com.heythere.movie_collection_api.Repo.MovieRepo;
import com.heythere.movie_collection_api.Service.MovieInfoService;

@RestController
@RequestMapping(value = "/")
public class ApiController {

    @Autowired
    private MovieRepo movieRepo;

    MovieInfoService movieInfoService = new MovieInfoService();

    final String OMDB_API_KEY = System.getenv("OMDB_API_KEY");
    String url = "https://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&";
    RestTemplate restTemplate = new RestTemplate();

    private HashMap<String, String> hitOMDBApi(String id, String title) {
        HashMap<String, String> responseHashMap = new HashMap<>();
        if (id != null) {
            @SuppressWarnings("unchecked")
            HashMap<String, String> response = restTemplate.getForObject(url+"i="+id, HashMap.class);
            responseHashMap = response;
        }
        else {
            @SuppressWarnings("unchecked")
            HashMap<String, String> response = restTemplate.getForObject(url+"t="+title, HashMap.class);
            responseHashMap = response;
        }
        return responseHashMap;
    }

    @GetMapping(value = {"", "movies", "movies/"})
    public List<Movie> getAllMoviesInfo() {
        return movieRepo.findAll();
    }

    @GetMapping(value = {"show", "show/"})
    public String showMovieInfo(@RequestParam(required = false) String id, 
                                @RequestParam(required = false) String title) 
    {
        if (id != null | title != null) {
            HashMap<String, String> movieInfo = hitOMDBApi(id, title);
            if (movieInfo.get("Title") == null) {
                return "Movie entry not found. Please try again.";
            }
            
            return movieInfo.get("Title") + ", " + movieInfo.get("Year") + ", Directed by " + movieInfo.get("Director") + ", Actors: " + movieInfo.get("Actors");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    @GetMapping(value = {"add", "add/"})
    public String addMovieInfo(@RequestParam(required = false) String id,
                                @RequestParam(required = false) String title) 
    {
        if (id != null | title != null) {
            HashMap<String, String> movieInfo = hitOMDBApi(id, title);
            if (movieInfo.get("Title") == null) {
                return "Movie entry not found. Please try again.";
            }
            
            Movie movie = movieInfoService.createMovieObject(movieInfo);
            movieRepo.save(movie);

            return movie.getTitle() + ", " + movie.getYear() + ", Directed by " + movie.getDirector() + ", Actors: " + movie.getActors();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    @GetMapping(value = {"find", "find/"})
    public List<Movie> getMovieInfo(@RequestParam Map<String, String> params){
        String id = params.get("id");
        String title = params.get("title");
        String director = params.get("director");
        String year = params.get("year");

        if (id != null) {
            return movieRepo.findById(id).stream().toList();
        }
        else if (title != null) {
            return movieInfoService.searchByTitle(movieRepo, params);
        }
        else if (director != null) {
            return movieInfoService.searchByDirector(movieRepo, params);
        }
        else if (year != null) {
            return movieRepo.findByYear(Integer.parseInt(year));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    @DeleteMapping(value = {"delete/{id}", "delete/{id}/"})
    public String deleteMovieInfo(@PathVariable("id") String id) {
        try {
            Movie movie = movieRepo.findById(id).get();
            movieRepo.delete(movie);
            return "Movie Entry for title " + movie.getTitle() + " deleted.";
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
            return "No movie entry found for the id " + id + ".";
        }
    }

}
