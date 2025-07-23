package belejki.com.mvc.reminder.web.binding;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ReminderBindingModel {

    private Long id;
    private Long userId;
    @NotBlank(message = "{reminder.name.cannot.be.blank}")
    @Size(min = 2, max = 50, message = "{reminder.name.between.2.and.50.characters}")
    private String name;
    @NotNull
    @Min(value = 1, message = "{reminder.importanceLevel.must.be.between.1.and.10.number}")
    @Max(value = 10, message = "{reminder.importanceLevel.must.be.between.1.and.10.number}")
    private Integer importanceLevel;
    @Size(max = 200, message = "{reminder.description.must.not.exceed.200.characters}")
    private String description;
    @NotNull
    @Future(message = "{reminder.expiration.date.must.be.in.the.future}")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")  // Optional if used in REST serialization
    private LocalDate expiration;
    private boolean expired;
    private boolean expiresSoon;
    private boolean expiresToday;
    private boolean monthMail;
    private boolean weekMail;
    private boolean todayMail;
}


