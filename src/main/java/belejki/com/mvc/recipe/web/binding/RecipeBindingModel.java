package belejki.com.mvc.recipe.web.binding;

import belejki.com.mvc.recipe.recipeIngredient.RecipeIngredientBindingModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeBindingModel {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 64, message = "{recipe.name.must.be.between.2.and.64.characters}")
    private String name;
    @Size(min = 10, max = 4000, message = "{instructions.must.be.between.10.and.4000.characters}")
    @NotBlank(message = "{recipe.must.have.instructions.how.to.make}")
    private String instructions;
//    @Pattern(regexp = "https?:\\/\\/[\\w\\-\\.~:\\/?#\\[\\]@!$&'()*+,;=%]+", message = "{wish.invalid.link}", )
    private String videoLink;
    @NotNull
    @Valid
    private List<RecipeIngredientBindingModel> ingredients;

}
