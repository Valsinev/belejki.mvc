package belejki.com.mvc.model.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingItemViewModel {

	private Long id;
	private String name;
	private String color;
	private BigDecimal price;
}
