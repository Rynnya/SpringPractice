package applications;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repositories.Database;

@NoArgsConstructor
@Component
public class TransferByPhoneApp {

    @Autowired
    private Database database;

    public void transfer(int sourceUserId, int destinationUserId, int amount) {
        database.transferMoney(sourceUserId, destinationUserId, amount);
    }

}
