package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.model.view.ReminderViewModel;
import belejki.com.mvc.repository.UserReminderRepository;
import belejki.com.mvc.service.JwtService;
import belejki.com.mvc.service.UserRemindersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRemindersServiceImpl implements UserRemindersService {

	private final UserReminderRepository userReminderRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserRemindersServiceImpl(UserReminderRepository userReminderRepository, ModelMapper modelMapper) {
		this.userReminderRepository = userReminderRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ReminderViewModel> getUserReminders() {
		List<UserReminderDto> all = userReminderRepository.findAll();

		return all.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public ReminderViewModel save(UserReminderBindingModel userReminderBindingModel) {

		UserReminderDto reminder = modelMapper.map(userReminderBindingModel, UserReminderDto.class);

		UserReminderDto saved = userReminderRepository.save(reminder);

		return modelMapper.map(saved, ReminderViewModel.class);
	}

	@Override
	public ReminderViewModel editReminder(Long id) {

		UserReminderDto edit = userReminderRepository.edit(id);

		return modelMapper.map(edit, ReminderViewModel.class);
	}

	@Override
	public ReminderViewModel updateReminder(Long id, UserReminderBindingModel userReminderBindingModel) {

		UserReminderDto reminder = modelMapper.map(userReminderBindingModel, UserReminderDto.class);

		UserReminderDto update = userReminderRepository.update(id, reminder);

		return modelMapper.map(update, ReminderViewModel.class);
	}

	@Override
	public ReminderViewModel deleteById(Long id) {

		UserReminderDto deleted = userReminderRepository.deleteById(id);

		return modelMapper.map(deleted, ReminderViewModel.class);
	}

	@Override
	public List<ReminderViewModel> searchByNameContaining(String searchValue) {

		List<UserReminderDto> founded = userReminderRepository.findAllByNameContaining(searchValue);

		return founded.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();

	}

	@Override
	public List<ReminderViewModel> getNotExpiredAndNotExpiresSoon() {

		List<UserReminderDto> notExpiredAndNotExpiresSoon = userReminderRepository.findAllNotExpiredAndNotExpiresSoon();

		return notExpiredAndNotExpiresSoon.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public List<ReminderViewModel> getExpiredReminders() {
		List<UserReminderDto> expiredReminders = userReminderRepository.findAllExpiredReminders();
		return expiredReminders.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}

	@Override
	public List<ReminderViewModel> getAlmostExpiredReminders() {

		List<UserReminderDto> almostExpiredReminders = userReminderRepository.findAllAlmostExpiredReminders();
		return almostExpiredReminders.stream()
				.map(userReminderDto -> modelMapper.map(userReminderDto, ReminderViewModel.class))
				.toList();
	}
}
