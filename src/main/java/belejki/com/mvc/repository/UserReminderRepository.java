package belejki.com.mvc.repository;

import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;

import java.util.List;

public interface UserReminderRepository {

	List<UserReminderDto> findAll();

	UserReminderDto save(UserReminderDto reminder, String jwtToken);

	UserReminderDto edit(Long id, String jwtToken);

	UserReminderDto update(Long id, UserReminderDto reminder, String token);

	UserReminderDto deleteById(Long id, String token);

	List<UserReminderDto> searchByNameContaining(String searchValue, String token);
}
