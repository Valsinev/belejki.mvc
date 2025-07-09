package belejki.com.mvc.model.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserWishBindingModel {

	private Long id;
	private Long userId;
	@NotNull
	@Size(min = 2, max = 24, message = "Wish description must be between 2 and 24 characters.")
	private String description;
	@NotNull
	private Double approximatePrice;
	@NotNull
	@Size(min = 2, max = 1024, message = "Wish link must be between 2 and 1024 characters.")
	private String link;
}
