package applications;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.Database;

@NoArgsConstructor
@Component
public class BankClientsApp {

    @Autowired
    private Database database;

    public boolean isUserExist(int userId) {
        return database.isUserExist(userId);
    }

}
