package be.kdg.programming5.presentation.mvc.controllers;

import be.kdg.programming5.domain.Session;
import be.kdg.programming5.security.AdminOnly;
import be.kdg.programming5.service.interfaces.SessionService;
import be.kdg.programming5.service.interfaces.UniversityCsvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class UniversityController {
    private final SessionService sessionService;
    private final UniversityCsvService universityCsvService;
    private final Logger logger = LoggerFactory.getLogger(UniversityController.class);

    public UniversityController(SessionService sessionService, UniversityCsvService universityCsvService) {
        this.sessionService = sessionService;
        this.universityCsvService = universityCsvService;
    }


    @GetMapping("/searchuniversity")
    @PreAuthorize("isAuthenticated()")
    public String showSearchUniversity(HttpSession httpSession) {
        logger.debug("Showing university search page...");
        sessionService.addSession(new Session("/searchuniversity", Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return "searchuniversity";
    }

    @GetMapping("universities_csv")
    @AdminOnly
    public ModelAndView uploadCsv() {
        var mav = new ModelAndView("universities_csv");
        mav.getModel().put("inProgress", false);
        return mav;
    }
    @PostMapping("universities_csv")
    @AdminOnly
    public ModelAndView uploadCsv(
            @RequestParam("universities_csv") MultipartFile file)
            throws IOException {
        universityCsvService.handleUniversitiesCsvUpload(file.getInputStream());
        var mav = new ModelAndView("universities_csv");
        mav.getModel().put("inProgress", true);
        return mav;
    }
}
