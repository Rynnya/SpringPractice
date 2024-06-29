package config;

import beans.Cat;
import beans.Dog;
import beans.Parrot;
import beans.Person;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Config {

    private final String KOKO_PARROT_NAME = "Koko";

    private final String KALE_PARROT_NAME = "Kale";

    @Bean
    public Cat getCat() {
        return new Cat("Vanessa");
    }

    @Bean
    public Dog getDog() {
        return new Dog("Mike");
    }

    @Bean(name = KOKO_PARROT_NAME)
    public Parrot getParrotKoko() {
        return new Parrot(KOKO_PARROT_NAME);
    }

    @Bean(name = KALE_PARROT_NAME)
    public Parrot getParrotKale() {
        return new Parrot(KALE_PARROT_NAME);
    }

    @Bean
    public Person getPerson() {
        return new Person(getCat(), getDog(), getParrotKoko(), getParrotKale(), "David");
    }

}
