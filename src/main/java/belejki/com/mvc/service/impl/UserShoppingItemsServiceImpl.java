package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.repository.UserShoppingItemRepository;
import belejki.com.mvc.service.UserShoppingItemsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Set<UserShoppingItemDto> getShoppingList() {

		return userShoppingItemRepository.getAll();

	}

	@Override
	public UserShoppingItemDto addShoppingItem(UserShoppingItemBindingModel userShoppingItemBindingModel) {

		UserShoppingItemDto item = modelMapper.map(userShoppingItemBindingModel, UserShoppingItemDto.class);
		return userShoppingItemRepository.add(item);

	}

	@Override
	public UserShoppingItemDto deleteItem(Long id) {

		return userShoppingItemRepository.deleteById(id);

	}

	@Override
	public void clearShoppingList() {

		userShoppingItemRepository.deleteAll();

	}
}
