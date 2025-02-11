package com.heythere.movie_info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MovieInfoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(MovieInfoApplication.class, args);
		new SpringApplicationBuilder(MovieInfoApplication.class).headless(false).run(args);
	}

}
