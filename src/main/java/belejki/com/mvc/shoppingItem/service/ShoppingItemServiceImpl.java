package belejki.com.mvc.shoppingItem.service;

import belejki.com.mvc.shoppingItem.web.binding.ShoppingItemBindingModel;
import belejki.com.mvc.shoppingItem.web.dto.ShoppingItemDto;
import belejki.com.mvc.shoppingItem.web.view.ShoppingItemViewModel;
import belejki.com.mvc.shoppingItem.repository.ShoppingItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingItemServiceImpl implements ShoppingItemService {

	private final ShoppingItemRepository shoppingItemRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public ShoppingItemServiceImpl(ShoppingItemRepository shoppingItemRepository, ModelMapper modelMapper) {
		this.shoppingItemRepository = shoppingItemRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Set<ShoppingItemViewModel> getShoppingList() {

		Set<ShoppingItemDto> all = shoppingItemRepository.getAll();

		return all.stream()
				.map(shoppingItemDto -> modelMapper.map(shoppingItemDto, ShoppingItemViewModel.class))
				.collect(Collectors.toSet());
	}

	@Override
	public ShoppingItemViewModel addShoppingItem(ShoppingItemBindingModel shoppingItemBindingModel) {

		ShoppingItemDto item = modelMapper.map(shoppingItemBindingModel, ShoppingItemDto.class);
		if (item.getPrice() == null) item.setPrice(BigDecimal.ZERO);

		ShoppingItemDto added = shoppingItemRepository.add(item);

		return modelMapper.map(added, ShoppingItemViewModel.class);
	}

	@Override
	public void deleteItem(Long id) {

		shoppingItemRepository.deleteById(id);
	}

	@Override
	public void clearShoppingList() {

		shoppingItemRepository.deleteAll();

	}

	@Override
	public BigDecimal getTotalPrice() {
		return shoppingItemRepository.getSumOfAllItemsPrice();
	}
}
