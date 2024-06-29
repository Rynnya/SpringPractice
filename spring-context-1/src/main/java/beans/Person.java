package beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Person {

    private Cat cat;

    private Dog dog;

    private Parrot parrotKoko;

    private Parrot parrotKale;

    private String name;

}
