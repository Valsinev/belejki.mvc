package belejki.com.mvc.service;

import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import jakarta.validation.Valid;
import org.springframework.web.client.RestClientException;

import java.util.List;

public interface UserRemindersService {

	List<UserReminderDto> getUserReminders(String jwtToken);

	UserReminderDto save(UserReminderBindingModel reminder, String jwtToken);

	UserReminderDto editReminder(Long id, String jwtToken);

	UserReminderDto updateReminder(Long id, UserReminderBindingModel reminder, String jwtToken);

	UserReminderDto deleteById(Long id, String jwtToken);

	List<UserReminderDto> searchByNameContaining(String searchValue, String jwtToken);
}
