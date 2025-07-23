package belejki.com.mvc.wish.web.view;

import lombok.Data;

@Data
public class WishViewModel {

	private Long id;
	private String description;
	private Double approximatePrice;
	private String link;
}
