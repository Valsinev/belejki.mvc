package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.model.dto.UserReminderDto;
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
	private final JwtService jwtService;
	private final ModelMapper modelMapper;

	@Autowired
	public UserRemindersServiceImpl(UserReminderRepository userReminderRepository, JwtService jwtService, ModelMapper modelMapper) {
		this.userReminderRepository = userReminderRepository;
		this.jwtService = jwtService;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<UserReminderDto> getUserReminders() {
		return userReminderRepository.findAll();
	}

	@Override
	public UserReminderDto save(UserReminderBindingModel userReminderBindingModel) {

		UserReminderDto reminder = modelMapper.map(userReminderBindingModel, UserReminderDto.class);

		return userReminderRepository.save(reminder);
	}

	@Override
	public UserReminderDto editReminder(Long id) {

		return userReminderRepository.edit(id);
	}

	@Override
	public UserReminderDto updateReminder(Long id, UserReminderBindingModel userReminderBindingModel) {

		UserReminderDto reminder = modelMapper.map(userReminderBindingModel, UserReminderDto.class);

		return userReminderRepository.update(id, reminder);
	}

	@Override
	public UserReminderDto deleteById(Long id) {

		return userReminderRepository.deleteById(id);

	}

	@Override
	public List<UserReminderDto> searchByNameContaining(String searchValue) {

		return userReminderRepository.searchByNameContaining(searchValue);

	}
}
