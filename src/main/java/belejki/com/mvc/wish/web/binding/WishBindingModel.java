package belejki.com.mvc.wish.web.binding;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WishBindingModel {

	private Long id;
	private Long userId;
	@NotBlank(message = "{wish.description.must.be.between.2.and.50.characters}")
	@Size(min = 2, max = 50, message = "{wish.description.must.be.between.2.and.50.characters}")
	private String description;
	@NotNull
	@DecimalMin(value = "1.0", message = "{wish.price.must.be.atleast.1}")
	private Double approximatePrice;
	@NotNull
	@Pattern(regexp = "https?:\\/\\/[\\w\\-\\.~:\\/?#\\[\\]@!$&'()*+,;=%]+", message = "{wish.invalid.link}")
	private String link;
}
