package belejki.com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {


    @GetMapping("/users")
    public String getAdminDashboard(Model model) {
        model.addAttribute("theYear", LocalDate.now().getYear());

        return "admin_users";
    }
}
