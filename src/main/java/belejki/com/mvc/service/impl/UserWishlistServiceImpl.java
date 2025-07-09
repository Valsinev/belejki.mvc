package belejki.com.mvc.service.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;
import belejki.com.mvc.repository.UserWishRepository;
import belejki.com.mvc.service.UserWishlistService;
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
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Service
public class UserWishlistServiceImpl implements UserWishlistService {

	private final UserWishRepository userWishRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserWishlistServiceImpl(UserWishRepository userWishRepository, ModelMapper modelMapper) {
		this.userWishRepository = userWishRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<WishDto> getWishlist(String jwtToken) {

		return userWishRepository.getAll(jwtToken);
	}

	@Override
	public WishDto createWish(UserWishBindingModel userWishBindingModel, String jwtToken) {

		WishDto wish = modelMapper.map(userWishBindingModel, WishDto.class);

		return userWishRepository.save(wish, jwtToken);

	}

	@Override
	public WishDto editWish(Long id, String jwtToken) {

		return userWishRepository.edit(id, jwtToken);

	}

	@Override
	public WishDto updateWish(Long id, UserWishBindingModel userWishBindingModel, String jwtToken) {

		WishDto wish = modelMapper.map(userWishBindingModel, WishDto.class);

		return userWishRepository.update(id, wish, jwtToken);

	}

	@Override
	public WishDto deleteWish(Long id, String jwtToken) {

		return userWishRepository.deleteById(id, jwtToken);

	}

	@Override
	public List<WishDto> searchByNameContaining(String searchValue, String jwtToken) {

		return userWishRepository.findAllByNameContaining(searchValue, jwtToken);

	}

	@Override
	public List<WishDto> filterByPriceLessThan(Long maxPrice, String jwtToken) {

		return userWishRepository.findAllByPriceLessThan(maxPrice, jwtToken);

	}
}
