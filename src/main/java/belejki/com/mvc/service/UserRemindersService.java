package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.model.view.ReminderViewModel;

import java.util.List;

public interface UserRemindersService {

	List<ReminderViewModel> getUserReminders();

	ReminderViewModel save(UserReminderBindingModel reminder);

	ReminderViewModel editReminder(Long id);

	ReminderViewModel updateReminder(Long id, UserReminderBindingModel reminder);

	ReminderViewModel deleteById(Long id);

	List<ReminderViewModel> searchByNameContaining(String searchValue);

	List<ReminderViewModel> getNotExpiredAndNotExpiresSoon();

	List<ReminderViewModel> getExpiredReminders();

	List<ReminderViewModel> getAlmostExpiredReminders();

}
