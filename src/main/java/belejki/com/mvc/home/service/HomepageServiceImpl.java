package belejki.com.mvc.home.service;

import belejki.com.mvc.reminder.web.dto.ReminderDto;
import belejki.com.mvc.reminder.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomepageServiceImpl implements HomepageService {
	private final ReminderRepository reminderRepository;

	@Autowired
	public HomepageServiceImpl(ReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}

	@Override
	public List<ReminderDto> getUserReminders() {

		return reminderRepository.findAll();

	}
}
