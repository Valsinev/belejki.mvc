package belejki.com.mvc.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserShoppingItemDto {

	private Long id;
	private Long userId;
	private String name;
	private String color;
	private BigDecimal price;
}
