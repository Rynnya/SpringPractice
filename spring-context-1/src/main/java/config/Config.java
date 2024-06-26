package config;

import beans.Cat;
import beans.Dog;
import beans.Parrot;
import beans.Person;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Config {

    @Bean
    public Cat getCat() {
        return new Cat("Vanessa");
    }

    @Bean
    public Dog getDog() {
        return new Dog("Mike");
    }

    @Bean(name = "Koko")
    public Parrot getParrotKoko() {
        return new Parrot("Koko");
    }

    @Bean(name = "Kale")
    public Parrot getParrotKale() {
        return new Parrot("Kale");
    }

    @Bean
    public Person getPerson() {
        return new Person(getCat(), getDog(), getParrotKoko(), getParrotKale(), "David");
    }

}
