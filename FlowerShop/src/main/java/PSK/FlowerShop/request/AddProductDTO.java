package PSK.FlowerShop.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class AddProductDTO {
    private UUID categoryId;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
