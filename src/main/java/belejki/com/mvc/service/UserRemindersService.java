package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;

import java.util.List;

public interface UserRemindersService {

	List<UserReminderDto> getUserReminders();

	UserReminderDto save(UserReminderBindingModel reminder);

	UserReminderDto editReminder(Long id);

	UserReminderDto updateReminder(Long id, UserReminderBindingModel reminder);

	UserReminderDto deleteById(Long id);

	List<UserReminderDto> searchByNameContaining(String searchValue);
}
