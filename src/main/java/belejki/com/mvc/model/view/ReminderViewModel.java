package belejki.com.mvc.model.view;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderViewModel {


	private Long id;
	private String name;
	private Integer importanceLevel;
	private String description;
	private LocalDate expiration;
}
