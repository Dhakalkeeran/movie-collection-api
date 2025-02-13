package com.heythere.movie_info.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController{
    
    @RequestMapping("/error")
    public String error() {
        return "Invalid request. Please try again.";
    }

}
