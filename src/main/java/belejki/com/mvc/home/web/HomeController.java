package belejki.com.mvc.home.web;

import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.reminder.web.view.ReminderViewModel;
import belejki.com.mvc.reminder.service.ReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    private final ReminderService reminderService;
    private final UserSessionInformation userinfo;

    @Autowired
	public HomeController(ReminderService reminderService, UserSessionInformation userSessionInformation) {
	    this.reminderService = reminderService;
	    this.userinfo = userSessionInformation;
    }


	@GetMapping("/")
    public String getHomePage(Model theModel) {
        theModel.addAttribute("theYear", LocalDate.now().getYear());
        return "index";
    }


    @GetMapping("/home")
    public String getUserDashboard(Model model) {

        if (userinfo.getJwtToken() == null) return "redirect:/login";

        //TODO: DONT FILTER GET THEM FROM DATABASE
        //ReminderService.getExpiredReminders()  ReminderService.getAlmostExpiredReminders() ReminderService.getFutureReminders()(not expired and not almost expired)
        List<ReminderViewModel> notExpiresSoon = reminderService.getNotExpiredAndNotExpiresSoon();
        List<ReminderViewModel> expired = reminderService.getExpiredReminders();
        List<ReminderViewModel> expiresSoon = reminderService.getAlmostExpiredReminders();

        int userRemindersCount = notExpiresSoon.size() + expired.size() + expiresSoon.size();


        model.addAttribute("theYear", LocalDate.now().getYear());
        model.addAttribute("user", userinfo.getFirstName().concat(" ")
                .concat(userinfo.getLastName()));
        model.addAttribute("remindersCount", userRemindersCount);
        model.addAttribute("expiredCount", expired.size());
        model.addAttribute("almost", expiresSoon.size());
        model.addAttribute("notSoon", notExpiresSoon.size());
        model.addAttribute("expiredReminders", expired);
        model.addAttribute("almostExpiredReminders", expiresSoon);
        model.addAttribute("notSoonExpiredReminders", notExpiresSoon);
        return "home.html";
    }




}
