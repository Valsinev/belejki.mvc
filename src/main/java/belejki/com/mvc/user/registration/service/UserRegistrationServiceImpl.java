package belejki.com.mvc.user.registration.service;

import belejki.com.mvc.user.registration.web.binding.RegisterBindingModel;
import belejki.com.mvc.user.registration.web.dto.RegistrationDto;
import belejki.com.mvc.user.repository.UserRepository;
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
	public RegistrationDto registerUser(RegisterBindingModel registerBindingModel) {

		RegistrationDto user = modelMapper.map(registerBindingModel, RegistrationDto.class);
		return userRepository.save(user);

	}
}
