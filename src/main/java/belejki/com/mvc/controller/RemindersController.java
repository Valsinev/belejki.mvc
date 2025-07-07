package belejki.com.mvc.controller;

import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.service.UserRemindersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        return userRemindersService.getUserReminders(model, session);
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("reminder", new ReminderDto());
        model.addAttribute("editing", false);
        return "create_reminder";
    }

    @PostMapping("/create")
    public String createNewReminder(@Valid @ModelAttribute("reminder") ReminderDto reminder,
                                    BindingResult result,
                                    HttpSession session,
                                    Model model) {

        return userRemindersService.createReminder(reminder, result, session, model);

    }


    @GetMapping("/edit/{id}")
    public String editReminder(@PathVariable("id") Long id, HttpSession session, Model model) {
        return userRemindersService.editReminder(id, session, model);

    }

    @PostMapping("/update/{id}")
    public String updateReminder(@PathVariable Long id,
                                 @Valid @ModelAttribute("reminder") ReminderDto reminder,
                                 BindingResult result,
                                 HttpSession session,
                                 Model model) {
        return userRemindersService.updateReminder(id, reminder, result, session, model);

    }

    @GetMapping("/delete/{id}")
    public String deleteReminder(@PathVariable Long id,
                                 HttpSession session) {
        return userRemindersService.deleteReminder(id, session);

    }

    @GetMapping("/search")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
                                         Model model,
                                         HttpSession session) {
        return userRemindersService.searchByNameContaining(searchValue, model, session);

    }


}
