package beans;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Person {

    @Autowired
    private Cat cat;

    @Autowired
    private Dog dog;

    @Autowired
    @Qualifier("Koko")
    private Parrot parrotKoko;

    @Autowired
    @Qualifier("Kale")
    private Parrot parrotKale;

    private String name = "David";

}
