package tests;

import beans.Cat;
import beans.Dog;
import beans.Parrot;
import beans.Person;
import main.Application;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
public class ContextTests {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("Verify that Cat component was successfully registered in Spring context")
    public void testCatComponentWasAdded() {
        Cat cat = context.getBean(Cat.class);

        assertEquals("Vanessa", cat.getName());
    }

    @Test
    @DisplayName("Verify that Dog component was successfully registered in Spring context")
    public void testDogComponentWasAdded() {
        Dog dog = context.getBean(Dog.class);

        assertEquals("Mike", dog.getName());
    }

    @Test
    @DisplayName("Verify that Parrot component was successfully registered in Spring context")
    public void testParrotComponentWasAdded() {
        Parrot parrot = context.getBean(Parrot.class);

        assertEquals("Koko", parrot.getName());
    }

    @Test
    @DisplayName("Verify that Person component was successfully registered in Spring context")
    public void testPersonComponentWasAdded() {
        Person person = context.getBean(Person.class);

        assertEquals("David", person.getName());
    }
}