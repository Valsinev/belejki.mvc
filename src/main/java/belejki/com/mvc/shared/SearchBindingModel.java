package belejki.com.mvc.shared;

import jakarta.validation.constraints.NotBlank;

public class SearchBindingModel {

	@NotBlank(message = "{blank.search.value.error}")
	private String searchValue;

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
}
