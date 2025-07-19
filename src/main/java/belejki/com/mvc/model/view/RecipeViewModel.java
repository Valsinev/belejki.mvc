package belejki.com.mvc.model.view;

import belejki.com.mvc.model.dto.RecipeIngredientDto;
import lombok.Data;

import java.util.List;

@Data
public class RecipeViewModel {
	private String name;
	private String instructions;
	private String videoLink;
	private List<RecipeIngredientViewModel> ingredients;
}
