package belejki.com.mvc.model.view;

import lombok.Data;

@Data
public class WishViewModel {

	private Long id;
	private String description;
	private Double approximatePrice;
	private String link;
}
