package belejki.com.mvc.friendship.web.binding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendshipBindingModel {
    private Long id;
    private Long friendId;
    @NotBlank
    @Pattern(regexp = "((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Username must be in valid email format.")
    private String friendUsername;
}
