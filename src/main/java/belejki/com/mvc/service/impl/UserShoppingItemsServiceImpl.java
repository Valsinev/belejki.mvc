package belejki.com.mvc.service.impl;

import belejki.com.mvc.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.repository.UserShoppingItemRepository;
import belejki.com.mvc.service.UserShoppingItemsService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Set;

@Service
public class UserShoppingItemsServiceImpl implements UserShoppingItemsService {

	private final UserShoppingItemRepository userShoppingItemRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserShoppingItemsServiceImpl(UserShoppingItemRepository userShoppingItemRepository, ModelMapper modelMapper) {
		this.userShoppingItemRepository = userShoppingItemRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Set<UserShoppingItemDto> getShoppingList(String jwtToken) {

		return userShoppingItemRepository.getAll(jwtToken);

	}

	@Override
	public UserShoppingItemDto addShoppingItem(UserShoppingItemBindingModel userShoppingItemBindingModel, String jwtToken) {

		UserShoppingItemDto item = modelMapper.map(userShoppingItemBindingModel, UserShoppingItemDto.class);
		return userShoppingItemRepository.add(item, jwtToken);

	}

	@Override
	public UserShoppingItemDto deleteItem(Long id, String jwtToken) {

		return userShoppingItemRepository.deleteById(id, jwtToken);

	}

	@Override
	public void clearShoppingList(String jwtToken) {

		userShoppingItemRepository.deleteAll(jwtToken);

	}
}
