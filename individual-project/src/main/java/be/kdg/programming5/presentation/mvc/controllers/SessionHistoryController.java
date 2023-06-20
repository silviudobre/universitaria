package be.kdg.programming5.presentation.mvc.controllers;

import be.kdg.programming5.domain.Session;
import be.kdg.programming5.service.interfaces.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class SessionHistoryController {
    private final SessionService sessionService;
    private final Logger logger = LoggerFactory.getLogger(SessionHistoryController.class);

    public SessionHistoryController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessionhistory")
    public ModelAndView showAllSessions(HttpSession httpSession){
        logger.debug("Showing the session history...");
        sessionService.addSession(new Session("/sessionhistory",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);

        var modelAndView = new ModelAndView();
        modelAndView.setViewName("sessionhistory");
        modelAndView.getModel().put("sessions", sessionService.getSessions());
        return modelAndView;
    }

    @GetMapping({"","/index","/"})
    public String showHome(HttpSession httpSession){
        sessionService.addSession(new Session("/index",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return "index";
    }
}
