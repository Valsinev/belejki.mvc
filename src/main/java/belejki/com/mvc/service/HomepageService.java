package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.UserReminderDto;

import java.util.List;

public interface HomepageService {
	List<UserReminderDto> getUserReminders();
}
