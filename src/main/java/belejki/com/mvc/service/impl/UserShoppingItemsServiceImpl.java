package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.model.view.ShoppingItemViewModel;
import belejki.com.mvc.repository.UserShoppingItemRepository;
import belejki.com.mvc.service.UserShoppingItemsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
	public Set<ShoppingItemViewModel> getShoppingList() {

		Set<UserShoppingItemDto> all = userShoppingItemRepository.getAll();

		return all.stream()
				.map(userShoppingItemDto -> modelMapper.map(userShoppingItemDto, ShoppingItemViewModel.class))
				.collect(Collectors.toSet());
	}

	@Override
	public ShoppingItemViewModel addShoppingItem(UserShoppingItemBindingModel userShoppingItemBindingModel) {

		UserShoppingItemDto item = modelMapper.map(userShoppingItemBindingModel, UserShoppingItemDto.class);

		UserShoppingItemDto added = userShoppingItemRepository.add(item);

		return modelMapper.map(added, ShoppingItemViewModel.class);
	}

	@Override
	public ShoppingItemViewModel deleteItem(Long id) {

		UserShoppingItemDto deleted = userShoppingItemRepository.deleteById(id);

		return modelMapper.map(deleted, ShoppingItemViewModel.class);
	}

	@Override
	public void clearShoppingList() {

		userShoppingItemRepository.deleteAll();

	}
}
