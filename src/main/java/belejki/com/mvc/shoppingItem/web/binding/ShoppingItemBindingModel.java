package belejki.com.mvc.shoppingItem.web.binding;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingItemBindingModel {
    private Long id;
    private Long userId;
    @NotNull
    @NotBlank(message = "{item.name.must.be.between.2.and.24.characters}")
    @Size(min = 2, max = 24, message = "{item.name.must.be.between.2.and.24.characters}")
    private String name;
    @NotNull
    private String color;
    private BigDecimal price;
}