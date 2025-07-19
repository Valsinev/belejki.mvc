package belejki.com.mvc.model.binding;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserShoppingItemBindingModel {
    private Long id;
    private Long userId;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item name must be between 2 and 24 characters.")
    private String name;
    @NotNull
    @NotBlank(message = "Item name is required.")
    @Size(min = 2, max = 24, message = "Item color must be between 2 and 24 characters.")
    private String color;

    @DecimalMin(value = "1.0")
    private BigDecimal price;
}