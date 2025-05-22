package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/user/reminders")
public class RemindersController {

    private final AppConfig appConfig;

    private final RestTemplate restTemplate;

    @Autowired
    public RemindersController(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getUserReminders(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/reminders",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
                }
        );
        List<ReminderDto> reminders = response.getBody().getContent();
        reminders.sort(Comparator.comparing(ReminderDto::getExpiration));

        model.addAttribute("reminders", reminders);

        return "user_reminders";
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
        if (result.hasErrors()) {
            model.addAttribute("editing", false);
            return "create_reminder";
        }

        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReminderDto> request = new HttpEntity<>(reminder, headers);

        restTemplate.postForEntity(
                appConfig.getBackendApiUrl() + "/user/reminders",
                request,
                Void.class);

        return "redirect:/user/reminders";
    }


    @GetMapping("/edit/{id}")
    public String editReminder(@PathVariable("id") Long id, HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ReminderDto> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/reminders/id/" + id,
                HttpMethod.GET,
                request,
                ReminderDto.class
                );

        ReminderDto reminder = response.getBody();
        model.addAttribute("reminder", reminder);
        model.addAttribute("editing", true);
        return "create_reminder";
    }

    @PostMapping("/update/{id}")
    public String updateReminder(@PathVariable Long id,
                                 @Valid @ModelAttribute("reminder") ReminderDto reminder,
                                 BindingResult result,
                                 HttpSession session,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editing", true);
            return "create_reminder";
        }

        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ReminderDto> request = new HttpEntity<>(reminder, headers);
        restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/reminders/" + id,
                HttpMethod.PUT,
                request,
                Void.class
        );

        return "redirect:/user/reminders";
    }

    @GetMapping("/delete/{id}")
    public String deleteReminder(@PathVariable Long id,
                                 HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    appConfig.getBackendApiUrl() + "/user/reminders/id/" + id,
                    HttpMethod.DELETE,
                    request,
                    Void.class
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/reminders/name/" + searchValue,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
                }
        );
        List<ReminderDto> reminders = response.getBody().getContent();
        reminders.sort(Comparator.comparing(ReminderDto::getExpiration));

        model.addAttribute("reminders", reminders);


        return "user_reminders";
    }


}
