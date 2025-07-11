package belejki.com.mvc.model.dto;

import lombok.Data;

@Data
public class WishDto {

    private Long id;
    private Long userId;
    private String description;
    private Double approximatePrice;
    private String link;
}
