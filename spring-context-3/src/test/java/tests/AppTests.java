package tests;

import applications.Application;
import main.MainApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MainApplication.class})
public class AppTests {

    private final int SOURCE_TEST_USER_ID = 0;

    private final int DESTINATION_TEST_USER_ID = 1;

    private final int TEST_AMOUNT = 100;

    @Autowired
    private Application application;

    @Test
    @DisplayName("Verify that Application component works correctly")
    public void testApplicationCorrectness() {
        assertDoesNotThrow(() -> application.run(SOURCE_TEST_USER_ID, DESTINATION_TEST_USER_ID, TEST_AMOUNT));
    }

}