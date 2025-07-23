package belejki.com.mvc.reminder.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReminderDto {

	private Long id;
	private Long userId;
	private String name;
	private Integer importanceLevel;
	private String description;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate expiration;
	private boolean expired;
	private boolean expiresSoon;
	private boolean expiresToday;
	private boolean monthMail;
	private boolean weekMail;
	private boolean todayMail;
}
