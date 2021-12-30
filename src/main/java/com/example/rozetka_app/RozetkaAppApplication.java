package com.example.rozetka_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication(
   exclude =  {
     DataSourceAutoConfiguration.class,
     WebMvcAutoConfiguration.class ,
     SecurityAutoConfiguration.class
   }
)
public class RozetkaAppApplication {
    public static void main(String[] args) {
       SpringApplication.run(RozetkaAppApplication.class, args);
    }
}
