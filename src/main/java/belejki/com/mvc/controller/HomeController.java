package belejki.com.mvc.controller;

import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.HomepageService;
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

    private final HomepageService homepageService;
    private final UserSessionInformation userinfo;

    @Autowired
	public HomeController(HomepageService homepageService, UserSessionInformation userSessionInformation) {
		this.homepageService = homepageService;
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

        List<UserReminderDto> reminders = homepageService.getUserReminders();

        List<UserReminderDto> expired = reminders.stream().filter(UserReminderDto::isExpired).toList();
        List<UserReminderDto> expiresSoon = reminders.stream().filter(UserReminderDto::isExpiresSoon).toList();


        model.addAttribute("theYear", LocalDate.now().getYear());
        model.addAttribute("user", userinfo.getFirstName().concat(" ")
                .concat(userinfo.getLastName()));
        model.addAttribute("remindersCount", reminders.size());
        model.addAttribute("expiredCount", expired.size());
        model.addAttribute("almost", expiresSoon.size());
        model.addAttribute("expiredReminders", expired);
        model.addAttribute("almostExpiredReminders", expiresSoon);
        return "home.html";
    }




}
