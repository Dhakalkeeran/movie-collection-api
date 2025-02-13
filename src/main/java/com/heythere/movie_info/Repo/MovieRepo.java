package com.heythere.movie_info.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heythere.movie_info.Models.Movie;

public interface MovieRepo extends JpaRepository<Movie, String> {
}
