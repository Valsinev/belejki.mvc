package belejki.com.mvc.shoppingItem.web.view;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingItemViewModel {

	private Long id;
	private String name;
	private String color;
	private BigDecimal price;
}
