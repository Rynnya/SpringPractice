package entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private long id;

    @NotBlank(message = "Имя продукта не должно быть пустым.")
    private String name;

    @NotNull(message = "Цена продукта должна быть указана.")
    private float price;

    @NotNull(message = "Количество продуктов должно быть указано.")
    private int amount;

}
