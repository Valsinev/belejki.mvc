package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.repository.UserReminderRepository;
import belejki.com.mvc.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomepageServiceImpl implements HomepageService {
	private final UserReminderRepository userReminderRepository;

	@Autowired
	public HomepageServiceImpl(UserReminderRepository userReminderRepository) {
		this.userReminderRepository = userReminderRepository;
	}

	@Override
	public List<UserReminderDto> getUserReminders() {

		return userReminderRepository.findAll();

	}
}
