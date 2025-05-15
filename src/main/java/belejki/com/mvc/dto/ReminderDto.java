package belejki.com.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReminderDto {
    private Long id;
    private Long userId;
    private String name;
    private Integer importanceLevel;
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiration;
    private boolean expired;
    private boolean expiresSoon;
    private boolean expiresToday;
    private boolean monthMail;
    private boolean weekMail;
    private boolean todayMail;

    // Getters and setters
}

