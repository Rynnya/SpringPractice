package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class Cart {

    private long id;

    private Set<Product> products;

    private String promocode;

}
