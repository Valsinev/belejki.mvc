package belejki.com.mvc.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

	private Long id;
	private Long userId;
	private String name;
	private String howToMake;
	private List<RecipeIngredientDto> ingredients;
}
