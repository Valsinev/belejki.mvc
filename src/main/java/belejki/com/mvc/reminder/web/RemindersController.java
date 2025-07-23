package belejki.com.mvc.reminder.web;

import belejki.com.mvc.reminder.web.binding.ReminderBindingModel;
import belejki.com.mvc.reminder.service.ReminderService;
import belejki.com.mvc.reminder.web.view.ReminderViewModel;
import belejki.com.mvc.shared.SearchBindingModel;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
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


	private final ReminderService reminderService;
	private final UserSessionInformation userinfo;

	@Autowired
	public RemindersController(ReminderService reminderService, UserSessionInformation userSessionInformation) {
		this.reminderService = reminderService;
		this.userinfo = userSessionInformation;
	}

	@GetMapping
	public String getUserReminders(Model model) {
		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (!model.containsAttribute("searchBindingModel")) {
			model.addAttribute("searchBindingModel", new SearchBindingModel());
		}

		List<ReminderViewModel> reminders = reminderService.getUserReminders();

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

		List<ReminderViewModel> reminders = reminderService.searchByNameContaining(searchBindingModel.getSearchValue());

		model.addAttribute("reminders", reminders);

		return "user_reminders";
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		if (!model.containsAttribute("reminderBindingModel")) {
			model.addAttribute("reminderBindingModel", new ReminderBindingModel());
		}
		model.addAttribute("editing", false);
		return "create_reminder";
	}

	@PostMapping("/create")
	public String createNewReminder(@Valid @ModelAttribute("reminderBindingModel") ReminderBindingModel reminderBindingModel,
	                                BindingResult bindingResult,
	                                RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("reminderBindingModel", reminderBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reminderBindingModel", bindingResult);
			redirectAttributes.addFlashAttribute("editing", false);
			return "redirect:/user/reminders/create";
		}


		try {
			reminderService.save(reminderBindingModel);
		} catch (RestClientException e) {
			redirectAttributes.addFlashAttribute("reminderBindingModel", reminderBindingModel);
			return "redirect:/user/reminders/create";
		}

		return "redirect:/user/reminders";

	}


	@GetMapping("/edit/{id}")
	public String editReminder(@PathVariable("id") Long id, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			ReminderViewModel reminder = reminderService.findById(id);
			model.addAttribute("reminderBindingModel", reminder);
			model.addAttribute("editing", true);
		} catch (RestClientException e) {
			return "redirect:/user/reminders/edit/" + id;
		}
		return "create_reminder";

	}

	@PostMapping("/update")
	public String updateReminder(@Valid @ModelAttribute("reminderBindingModel") ReminderBindingModel reminder,
	                             BindingResult result,
	                             Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", true);
			return "create_reminder";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			reminderService.updateReminder(reminder);
		} catch (RestClientException e) {
			return "redirect:/user/reminders";
		}

		return "redirect:/user/reminders";
	}

	@GetMapping("/delete/{id}")
	public String deleteReminder(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			reminderService.deleteById(id);

		} catch (RestClientException e) {
			return "redirect:/user/reminders?error=delete";
		}
		return "redirect:/user/reminders";

	}


}
