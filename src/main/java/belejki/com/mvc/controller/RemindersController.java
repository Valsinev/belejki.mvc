package belejki.com.mvc.controller;

import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.service.UserRemindersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user/reminders")
public class RemindersController {


	private final UserRemindersService userRemindersService;

	@Autowired
	public RemindersController(UserRemindersService userRemindersService) {
		this.userRemindersService = userRemindersService;
	}

	@GetMapping
	public String getUserReminders(Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

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
	                                HttpSession session,
	                                Model model,
	                                RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("editing", false);
			return "create_reminder";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";


		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("reminder", reminder);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reminder", reminder);
			return "redirect:/user/reminders/create";
		}

		try {
			userRemindersService.save(reminder, token);
		} catch (RestClientException e) {
			redirectAttributes.addFlashAttribute("reminder", reminder);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reminder", reminder);
			return "redirect:/user/reminders/create";
		}

		return "redirect:/user/reminders";

	}


	@GetMapping("/edit/{id}")
	public String editReminder(@PathVariable("id") Long id, HttpSession session, Model model) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			UserReminderDto reminder = userRemindersService.editReminder(id, token);
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
	                             HttpSession session,
	                             Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", true);
			return "create_reminder";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userRemindersService.updateReminder(id, reminder, token);
		} catch (RestClientException e) {
			return "redirect:/user/reminders/update/" + id;
		}

		return "redirect:/user/reminders";
	}

	@GetMapping("/delete/{id}")
	public String deleteReminder(@PathVariable Long id,
	                             HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userRemindersService.deleteById(id, token);

		} catch (RestClientException e) {
			return "redirect:/user/reminders?error=delete";
		}
		return "redirect:/user/reminders";

	}

	@GetMapping("/search")
	public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
	                                     Model model,
	                                     HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		List<UserReminderDto> reminders = userRemindersService.searchByNameContaining(searchValue, token);

		model.addAttribute("reminders", reminders);

		return "user_reminders";
	}


}
