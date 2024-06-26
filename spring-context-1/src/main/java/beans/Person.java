package beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

    private Cat cat;

    private Dog dog;

    private Parrot parrotKoko;

    private Parrot parrotKale;

    private String name;

    public Person(Cat cat, Dog dog, Parrot parrotKoko, Parrot parrotKale, String name) {
        this.cat = cat;
        this.dog = dog;
        this.parrotKoko = parrotKoko;
        this.parrotKale = parrotKale;
        this.name = name;
    }

}
