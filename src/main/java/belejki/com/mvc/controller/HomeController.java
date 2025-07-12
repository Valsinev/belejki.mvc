package belejki.com.mvc.controller;

import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.model.view.ReminderViewModel;
import belejki.com.mvc.service.HomepageService;
import belejki.com.mvc.service.UserRemindersService;
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

    private final UserRemindersService userRemindersService;
    private final UserSessionInformation userinfo;

    @Autowired
	public HomeController(UserRemindersService userRemindersService, UserSessionInformation userSessionInformation) {
	    this.userRemindersService = userRemindersService;
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
        //UserRemindersService.getExpiredReminders()  UserRemindersService.getAlmostExpiredReminders() UserRemindersService.getFutureReminders()(not expired and not almost expired)
        List<ReminderViewModel> notExpiresSoon = userRemindersService.getNotExpiredAndNotExpiresSoon();
        List<ReminderViewModel> expired = userRemindersService.getExpiredReminders();
        List<ReminderViewModel> expiresSoon = userRemindersService.getAlmostExpiredReminders();

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
