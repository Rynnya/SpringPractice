package applications;

import aspects.NotEmpty;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ArgumentsApp {

    @NotEmpty
    public void withoutArguments() {

    }

    public void withoutAnnotation(String string) {

    }

    @NotEmpty
    public void withInteger(Integer integer) {

    }

    @NotEmpty
    public void withString(String string) {

    }

    @NotEmpty
    public void withCollectionOfStrings(Collection<String> collection) {

    }

    @NotEmpty
    public void withBothCollectionAndString(Collection<String> collection, String string) {

    }

}
