package main;

import config.Config;
import org.springframework.boot.SpringApplication;

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }

}