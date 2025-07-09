package belejki.com.mvc.controller;

import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.service.HomepageService;
import belejki.com.mvc.service.JwtService;
import jakarta.servlet.http.HttpSession;
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
    private final JwtService jwtService;

    @Autowired
	public HomeController(HomepageService homepageService, JwtService jwtService) {
		this.homepageService = homepageService;
	    this.jwtService = jwtService;
    }


	@GetMapping("/")
    public String getHomePage(Model theModel) {
        theModel.addAttribute("theYear", LocalDate.now().getYear());
        return "index";
    }


    @GetMapping("/home")
    public String getUserDashboard(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";
        String username = jwtService.extractUsername(token);

        List<UserReminderDto> reminders = homepageService.getUserReminders(username, token);

        List<UserReminderDto> expired = reminders.stream().filter(UserReminderDto::isExpired).toList();
        List<UserReminderDto> expiresSoon = reminders.stream().filter(UserReminderDto::isExpiresSoon).toList();


        model.addAttribute("theYear", LocalDate.now().getYear());
        model.addAttribute("user", username);
        model.addAttribute("remindersCount", reminders.size());
        model.addAttribute("expiredCount", expired.size());
        model.addAttribute("almost", expiresSoon.size());
        model.addAttribute("expiredReminders", expired);
        model.addAttribute("almostExpiredReminders", expiresSoon);
        return "home";
    }




}
