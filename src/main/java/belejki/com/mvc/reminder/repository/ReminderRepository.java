package belejki.com.mvc.reminder.repository;

import belejki.com.mvc.reminder.web.dto.ReminderDto;

import java.util.List;

public interface ReminderRepository {

	List<ReminderDto> findAll();

	ReminderDto save(ReminderDto reminder);

	ReminderDto findById(Long id);

	ReminderDto update(ReminderDto reminder);

	void deleteById(Long id);

	List<ReminderDto> findAllByNameContaining(String searchValue);

	List<ReminderDto> findAllNotExpiredAndNotExpiresSoon();

	List<ReminderDto> findAllExpiredReminders();

	List<ReminderDto> findAllAlmostExpiredReminders();

}
