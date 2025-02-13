package com.heythere.movie_info.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heythere.movie_info.Models.Movie;

public interface MovieRepo extends JpaRepository<Movie, String> {

    Optional<Movie> findById(String id);

    List<Movie> findByTitle(String title);
    List<Movie> findByTitleAndDirector(String title, String director);
    List<Movie> findByTitleAndYear(String title, int year);
    List<Movie> findByTitleAndDirectorAndYear(String title, String director, int year);

    List<Movie> findByDirector(String director);
    List<Movie> findByDirectorAndYear(String director, int year);

    List<Movie> findByYear(int year);
}
