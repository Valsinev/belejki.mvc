package belejki.com.mvc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WishDto {

    private Long id;
    private Long userId;
    private String description;
    private Double approximatePrice;
    private String link;
}
