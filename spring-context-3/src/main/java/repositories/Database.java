package repositories;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Database {

    private final List<Integer> VALID_USER_IDS = List.of(0, 1, 2, 3);

    public boolean isUserExist(int userId) {
        return VALID_USER_IDS.contains(userId);
    }

    public void transferMoney(int sourceUserId, int destinationUserId, int amount) {

    }

}
