package tests;

import applications.BankClientsApp;
import applications.TransferByPhoneApp;
import main.Application;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
public class AppTests {

    @Autowired
    private BankClientsApp bankClientsApp;

    @Autowired
    private TransferByPhoneApp transferByPhoneApp;

    @Test
    @DisplayName("Verify that BankClientsApp component correctly detects whether or not provided user is client")
    public void testBankClientsAppCorrectness() {
        assertTrue(bankClientsApp.isUserExist(0));
        assertTrue(bankClientsApp.isUserExist(1));
        assertFalse(bankClientsApp.isUserExist(10));
        assertFalse(bankClientsApp.isUserExist(-1));
    }

    @Test
    @DisplayName("Verify that TransferByPhoneApp component correctly works with all preconditions being correct")
    public void testTransferByPhoneAppCorrectness() {
        assertDoesNotThrow(() -> transferByPhoneApp.transfer(0, 1, 10));
    }

}