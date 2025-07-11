package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.UserRegistrationDto;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.repository.UserRepository;
import belejki.com.mvc.service.UserRegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
