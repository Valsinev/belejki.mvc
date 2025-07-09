package belejki.com.mvc.service;

import belejki.com.mvc.dto.UserReminderDto;

import java.util.List;

public interface HomepageService {
	List<UserReminderDto> getUserReminders(String username, String token);
}
