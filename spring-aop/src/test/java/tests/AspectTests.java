package tests;

import applications.ArgumentsApp;
import main.Application;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
public class AspectTests {

    @Autowired
    private ArgumentsApp argumentsApp;

    @Test
    @DisplayName("Verify that ArgumentsApp doesn't trigger any exceptions with NotEmptyAspect when calling method without arguments")
    public void testArgumentsAppDoesNotThrowWhenNoArgumentsProvided() {
        assertDoesNotThrow(() -> argumentsApp.withoutArguments());
    }

    @Test
    @DisplayName("Verify that ArgumentsApp doesn't trigger any exceptions with NotEmptyAspect when calling method without NotEmpty annotation")
    public void testArgumentsAppDoesNotThrowWhenNoAnnotationApplied() {
        assertDoesNotThrow(() -> argumentsApp.withoutAnnotation(null));
    }

    @Test
    @DisplayName("Verify that ArgumentsApp doesn't trigger any exceptions with NotEmptyAspect when calling method with arguments, types of which is different than String and Collection")
    public void testArgumentsAppDoesNotThrowWhenNoVerifiableClassesInArguments() {
        assertDoesNotThrow(() -> argumentsApp.withInteger(0));
    }

    @Test
    @DisplayName("Verify that ArgumentsApp doesn't trigger any exceptions with NotEmptyAspect when calling method with valid argument(s)")
    public void testArgumentsAppDoesNotThrowWhenArgumentsAreValid() {
        assertDoesNotThrow(() -> argumentsApp.withString("Hello Spring!"));
        assertDoesNotThrow(() -> argumentsApp.withCollectionOfStrings(List.of("Hello Spring!")));
        assertDoesNotThrow(() -> argumentsApp.withBothCollectionAndString(List.of("Hello Spring!"), "Hello Spring!"));
    }

    @Test
    @DisplayName("Verify that ArgumentsApp does trigger IllegalArgumentException with NotEmptyAspect when calling method with invalid argument(s)")
    public void testArgumentsAppDoesThrowWhenArgumentsAreInvalid() {
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withString(null));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withString(""));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withCollectionOfStrings(null));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withCollectionOfStrings(List.of()));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withBothCollectionAndString(List.of(), null));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withBothCollectionAndString(List.of(), "Hello Spring!"));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withBothCollectionAndString(List.of("Hello Spring!"), null));
        assertThrowsExactly(IllegalArgumentException.class, () -> argumentsApp.withBothCollectionAndString(List.of("Hello Spring!"), ""));
    }

}