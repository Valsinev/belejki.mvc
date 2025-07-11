package belejki.com.mvc.controller;

import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.UserRemindersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user/reminders")
public class RemindersController {


	private final UserRemindersService userRemindersService;
	private final UserSessionInformation userinfo;

	@Autowired
	public RemindersController(UserRemindersService userRemindersService, UserSessionInformation userSessionInformation) {
		this.userRemindersService = userRemindersService;
		this.userinfo = userSessionInformation;
	}

	@GetMapping
	public String getUserReminders(Model model) {
		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<UserReminderDto> reminders = userRemindersService.getUserReminders();

		model.addAttribute("theYear", LocalDate.now().getYear());
		model.addAttribute("reminders", reminders);

		return "user_reminders";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("theYear", LocalDate.now().getYear());
		model.addAttribute("reminder", new UserReminderBindingModel());
		model.addAttribute("editing", false);
		return "create_reminder";
	}

	@PostMapping("/create")
	public String createNewReminder(@Valid @ModelAttribute("reminder") UserReminderBindingModel reminder,
	                                BindingResult result,
	                                Model model,
	                                RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("editing", false);
			return "create_reminder";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";


		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("reminder", reminder);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reminder", reminder);
			return "redirect:/user/reminders/create";
		}

		try {
			userRemindersService.save(reminder);
		} catch (RestClientException e) {
			redirectAttributes.addFlashAttribute("reminder", reminder);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reminder", reminder);
			return "redirect:/user/reminders/create";
		}

		return "redirect:/user/reminders";

	}


	@GetMapping("/edit/{id}")
	public String editReminder(@PathVariable("id") Long id, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			UserReminderDto reminder = userRemindersService.editReminder(id);
			model.addAttribute("reminder", reminder);
			model.addAttribute("editing", true);
		} catch (RestClientException e) {
			return "redirect:/user/reminders/edit/" + id;
		}
		return "create_reminder";

	}

	@PostMapping("/update/{id}")
	public String updateReminder(@PathVariable Long id,
	                             @Valid @ModelAttribute("reminder") UserReminderBindingModel reminder,
	                             BindingResult result,
	                             Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", true);
			return "create_reminder";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userRemindersService.updateReminder(id, reminder);
		} catch (RestClientException e) {
			return "redirect:/user/reminders/update/" + id;
		}

		return "redirect:/user/reminders";
	}

	@GetMapping("/delete/{id}")
	public String deleteReminder(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userRemindersService.deleteById(id);

		} catch (RestClientException e) {
			return "redirect:/user/reminders?error=delete";
		}
		return "redirect:/user/reminders";

	}

	@GetMapping("/search")
	public String searchByNameContaining(@RequestParam("searchValue") String searchValue, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/user/dashboard";

		List<UserReminderDto> reminders = userRemindersService.searchByNameContaining(searchValue);

		model.addAttribute("reminders", reminders);

		return "user_reminders";
	}


}
