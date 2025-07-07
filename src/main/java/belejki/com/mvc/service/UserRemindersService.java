package belejki.com.mvc.service;

import belejki.com.mvc.dto.ReminderDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface UserRemindersService {
	String getUserReminders(Model model, HttpSession session);

	String createReminder(@Valid ReminderDto reminder, BindingResult result, HttpSession session, Model model);

	String editReminder(Long id, HttpSession session, Model model);

	String updateReminder(Long id, @Valid ReminderDto reminder, BindingResult result, HttpSession session, Model model);

	String deleteReminder(Long id, HttpSession session);

	String searchByNameContaining(String searchValue, Model model, HttpSession session);
}
