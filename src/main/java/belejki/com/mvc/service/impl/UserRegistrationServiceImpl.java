package belejki.com.mvc.service.impl;

import belejki.com.mvc.dto.UserRegistrationDto;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.repository.UserRepository;
import belejki.com.mvc.service.UserRegistrationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserRegistrationServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}


	@Override
	public UserRegistrationDto registerUser(UserRegisterBindingModel userRegisterBindingModel) {

		UserRegistrationDto user = modelMapper.map(userRegisterBindingModel, UserRegistrationDto.class);
		return userRepository.save(user);

	}
}
