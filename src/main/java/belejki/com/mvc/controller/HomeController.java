package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.service.AuthService;
import belejki.com.mvc.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Locale;

@Controller
@Slf4j
public class HomeController {


    @GetMapping("/")
    public String getHomePage(Model theModel) {
        theModel.addAttribute("theYear", LocalDate.now().getYear());
        return "home";
    }




}
