package belejki.com.mvc.repository;

import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;

import java.util.List;

public interface UserReminderRepository {

	List<UserReminderDto> findAll();

	UserReminderDto save(UserReminderDto reminder);

	UserReminderDto edit(Long id);

	UserReminderDto update(Long id, UserReminderDto reminder);

	UserReminderDto deleteById(Long id);

	List<UserReminderDto> searchByNameContaining(String searchValue);
}
