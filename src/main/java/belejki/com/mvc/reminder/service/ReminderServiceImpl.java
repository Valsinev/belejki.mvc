package belejki.com.mvc.reminder.service;

import belejki.com.mvc.reminder.web.binding.ReminderBindingModel;
import belejki.com.mvc.reminder.web.view.ReminderViewModel;
import belejki.com.mvc.reminder.repository.ReminderRepository;
import belejki.com.mvc.reminder.web.dto.ReminderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

	private final ReminderRepository reminderRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public ReminderServiceImpl(ReminderRepository reminderRepository, ModelMapper modelMapper) {
		this.reminderRepository = reminderRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ReminderViewModel> getUserReminders() {
		List<ReminderDto> all = reminderRepository.findAll();

		return all.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public ReminderViewModel save(ReminderBindingModel reminderBindingModel) {

		ReminderDto reminder = modelMapper.map(reminderBindingModel, ReminderDto.class);

		ReminderDto saved = reminderRepository.save(reminder);

		return modelMapper.map(saved, ReminderViewModel.class);
	}

	@Override
	public ReminderViewModel findById(Long id) {

		ReminderDto edit = reminderRepository.findById(id);

		return modelMapper.map(edit, ReminderViewModel.class);
	}

	@Override
	public ReminderViewModel updateReminder(ReminderBindingModel reminderBindingModel) {

		ReminderDto reminder = modelMapper.map(reminderBindingModel, ReminderDto.class);

		ReminderDto update = reminderRepository.update(reminder);

		return modelMapper.map(update, ReminderViewModel.class);
	}


	@Override
	public List<ReminderViewModel> searchByNameContaining(String searchValue) {

		List<ReminderDto> founded = reminderRepository.findAllByNameContaining(searchValue);

		return founded.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();

	}

	@Override
	public List<ReminderViewModel> getNotExpiredAndNotExpiresSoon() {

		List<ReminderDto> notExpiredAndNotExpiresSoon = reminderRepository.findAllNotExpiredAndNotExpiresSoon();

		return notExpiredAndNotExpiresSoon.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public List<ReminderViewModel> getExpiredReminders() {
		List<ReminderDto> expiredReminders = reminderRepository.findAllExpiredReminders();
		return expiredReminders.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public List<ReminderViewModel> getAlmostExpiredReminders() {

		List<ReminderDto> almostExpiredReminders = reminderRepository.findAllAlmostExpiredReminders();
		return almostExpiredReminders.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public void deleteById(Long id) {

		reminderRepository.deleteById(id);
	}
}
