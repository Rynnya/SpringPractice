package config;

import beans.Parrot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "beans")
public class Config {

    @Bean(name = "Koko")
    public Parrot getParrotKoko() {
        return new Parrot("Koko");
    }

    @Bean(name = "Kale")
    public Parrot getParrotKale() {
        return new Parrot("Kale");
    }

}