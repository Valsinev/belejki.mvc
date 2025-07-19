package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.SearchBindingModel;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.model.view.ReminderViewModel;
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

		if (!model.containsAttribute("searchBindingModel")) {
			model.addAttribute("searchBindingModel", new SearchBindingModel());
		}

		List<ReminderViewModel> reminders = userRemindersService.getUserReminders();

		model.addAttribute("theYear", LocalDate.now().getYear());
		model.addAttribute("reminders", reminders);

		return "user_reminders";
	}

	@GetMapping("/search")
	public String searchByNameContaining(@Valid @ModelAttribute("searchBindingModel") SearchBindingModel searchBindingModel,
	                                     BindingResult bindingResult,
	                                     Model model,
	                                     RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchBindingModel", bindingResult);
			redirectAttributes.addFlashAttribute("searchBindingModel", searchBindingModel);
			return "redirect:/user/reminders";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/user/dashboard";

		List<ReminderViewModel> reminders = userRemindersService.searchByNameContaining(searchBindingModel.getSearchValue());

		model.addAttribute("reminders", reminders);

		return "user_reminders";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		if (!model.containsAttribute("userReminderBindingModel")) {
			model.addAttribute("userReminderBindingModel", new UserReminderBindingModel());
		}
		model.addAttribute("theYear", LocalDate.now().getYear());
		model.addAttribute("editing", false);
		return "create_reminder";
	}

	@PostMapping("/create")
	public String createNewReminder(@Valid @ModelAttribute("userReminderBindingModel") UserReminderBindingModel userReminderBindingModel,
	                                BindingResult bindingResult,
	                                RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("userReminderBindingModel", userReminderBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userReminderBindingModel", bindingResult);
			redirectAttributes.addFlashAttribute("editing", false);
			return "redirect:/user/reminders/create";
		}


		try {
			userRemindersService.save(userReminderBindingModel);
		} catch (RestClientException e) {
			redirectAttributes.addFlashAttribute("userReminderBindingModel", userReminderBindingModel);
			return "redirect:/user/reminders/create";
		}

		return "redirect:/user/reminders";

	}


	@GetMapping("/edit/{id}")
	public String editReminder(@PathVariable("id") Long id, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			ReminderViewModel reminder = userRemindersService.editReminder(id);
			model.addAttribute("userReminderBindingModel", reminder);
			model.addAttribute("editing", true);
		} catch (RestClientException e) {
			return "redirect:/user/reminders/edit/" + id;
		}
		return "create_reminder";

	}

	@PostMapping("/update/{id}")
	public String updateReminder(@PathVariable Long id,
	                             @Valid @ModelAttribute("userReminderBindingModel") UserReminderBindingModel reminder,
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


}
