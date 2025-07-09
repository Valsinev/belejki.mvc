package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.repository.UserReminderRepository;
import belejki.com.mvc.service.JwtService;
import belejki.com.mvc.service.UserRemindersService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Comparator;
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
	public List<UserReminderDto> getUserReminders(String jwtToken) {
		return userReminderRepository.findAll(jwtToken);
	}

	@Override
	public UserReminderDto save(UserReminderBindingModel userReminderBindingModel, String jwtToken) {

		UserReminderDto reminder = modelMapper.map(userReminderBindingModel, UserReminderDto.class);

		return userReminderRepository.save(reminder, jwtToken);
	}

	@Override
	public UserReminderDto editReminder(Long id, String jwtToken) {

		return userReminderRepository.edit(id, jwtToken);
	}

	@Override
	public UserReminderDto updateReminder(Long id, UserReminderBindingModel userReminderBindingModel, String token) {

		UserReminderDto reminder = modelMapper.map(userReminderBindingModel, UserReminderDto.class);

		return userReminderRepository.update(id, reminder, token);
	}

	@Override
	public UserReminderDto deleteById(Long id, String token) {

		return userReminderRepository.deleteById(id, token);

	}

	@Override
	public List<UserReminderDto> searchByNameContaining(String searchValue, String token) {

		return userReminderRepository.searchByNameContaining(searchValue, token);

	}
}
