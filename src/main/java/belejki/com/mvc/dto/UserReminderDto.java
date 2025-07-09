package belejki.com.mvc.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserReminderDto {

	private Long id;
	private Long userId;
	private String name;
	private Integer importanceLevel;
	private String description;
	private LocalDate expiration;
	private boolean expired;
	private boolean expiresSoon;
	private boolean expiresToday;
	private boolean monthMail;
	private boolean weekMail;
	private boolean todayMail;
}
