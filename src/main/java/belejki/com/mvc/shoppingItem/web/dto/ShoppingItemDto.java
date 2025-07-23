package belejki.com.mvc.shoppingItem.web.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShoppingItemDto {

	private Long id;
	private Long userId;
	private String name;
	private String color;
	private BigDecimal price;
}
