package belejki.com.mvc.reminder.service;

import belejki.com.mvc.reminder.web.binding.ReminderBindingModel;
import belejki.com.mvc.reminder.web.view.ReminderViewModel;

import java.util.List;

public interface ReminderService {

	List<ReminderViewModel> getUserReminders();

	ReminderViewModel save(ReminderBindingModel reminder);

	ReminderViewModel findById(Long id);

	ReminderViewModel updateReminder(ReminderBindingModel reminder);

	List<ReminderViewModel> searchByNameContaining(String searchValue);

	List<ReminderViewModel> getNotExpiredAndNotExpiresSoon();

	List<ReminderViewModel> getExpiredReminders();

	List<ReminderViewModel> getAlmostExpiredReminders();

	void deleteById(Long id);

}
