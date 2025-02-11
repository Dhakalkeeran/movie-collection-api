package com.heythere.movie_info.Controller;

import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/")
public class ApiController {

    final String OMDB_API_KEY = System.getenv("OMDB_API_KEY");
    String url = "https://www.omdbapi.com/?apikey=" + OMDB_API_KEY + "&";

    @GetMapping
    public String getMovieDataByTitle(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String id) {
        if (title != null) {
            RestTemplate restTemplate = new RestTemplate();
            @SuppressWarnings("unchecked")
            Map<String, String> response = restTemplate.getForObject(url+"t="+title, Map.class);
            this.displayImage(response.get("Poster"));
            return "Welcome to the information sharing of movie title: " + response.get("Title");
        }
        else if (id != null) {
            return "Welcome to the information sharing of movie ID: " + id;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    public void displayImage(String urlString) {
        if (!GraphicsEnvironment.isHeadless()) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("Movie Poster");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(500, 500);
            
            URI uri;
            try {
                uri = new URI(urlString);
                URL url;
                url = uri.toURL();

                ImageIcon imageIcon = new ImageIcon(url);

                // Add to JLabel
                JLabel label = new JLabel(imageIcon);
                frame.add(label, BorderLayout.CENTER);

                // Show frame
                frame.setVisible(true);
            } 
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Headless Environment");
        }
    }
}
