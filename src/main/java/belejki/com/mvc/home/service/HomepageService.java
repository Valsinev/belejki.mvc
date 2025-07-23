package belejki.com.mvc.home.service;

import belejki.com.mvc.reminder.web.dto.ReminderDto;

import java.util.List;

public interface HomepageService {
	List<ReminderDto> getUserReminders();
}
