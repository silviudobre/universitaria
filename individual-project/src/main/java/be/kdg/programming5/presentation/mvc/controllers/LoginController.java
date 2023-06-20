package be.kdg.programming5.presentation.mvc.controllers;

import be.kdg.programming5.security.CustomUserDetails;
import be.kdg.programming5.service.interfaces.SessionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class LoginController {
    private final SessionService sessionService;

    public LoginController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/login")
    public ModelAndView showLogInForm(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        var modelAndView = new ModelAndView();
        if (customUserDetails != null) {
            modelAndView.setViewName("index");
            return modelAndView;
        }

        sessionService.getSessions().clear();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
