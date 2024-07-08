package entities;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private long id;

    @NotBlank(message = "Имя пользователя должно быть указано.")
    private String name;

    @NotBlank(message = "Логин пользователя должен быть указан.")
    private String login;

    @NotBlank(message = "Пароль пользователя должен быть указан.")
    private String password;

    @NotBlank(message = "Почта пользователя должен быть указан.")
    private String email;

}
