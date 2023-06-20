package be.kdg.programming5.presentation.mvc.controllers;

import be.kdg.programming5.domain.Session;
import be.kdg.programming5.presentation.mvc.viewmodels.NewCampusViewModel;
import be.kdg.programming5.security.AdminOnly;
import be.kdg.programming5.service.interfaces.CampusService;
import be.kdg.programming5.service.interfaces.SessionService;
import be.kdg.programming5.service.interfaces.TeacherService;
import be.kdg.programming5.service.interfaces.UniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class CampusController {
    private final TeacherService teacherService;
    private final CampusService campusService;
    private final UniversityService universityService;
    private final SessionService sessionService;
    private final Logger logger = LoggerFactory.getLogger(CampusController.class);

    public CampusController(TeacherService teacherService, CampusService campusService, UniversityService universityService, SessionService sessionService) {
        this.teacherService = teacherService;
        this.campusService = campusService;
        this.universityService = universityService;
        this.sessionService = sessionService;
    }

    @GetMapping("/campuses")
    @AdminOnly
    public ModelAndView showAllCampuses(HttpSession httpSession){
        logger.debug("Showing all campuses from service...");
        var modelAndView = new ModelAndView();
        var campuses = campusService.getAllCampuses();
        modelAndView.setViewName("campuses");
        modelAndView.getModel().put("campuses", campuses);
        sessionService.addSession(new Session("/campuses",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return modelAndView;
    }

    @GetMapping("/addcampus")
    @AdminOnly
    public String showAddCampusForm(HttpSession httpSession, Model model) {
        logger.debug("Showing form to add campus...");
        var campus = new NewCampusViewModel();
        var universities = universityService.getAllUniversities();
        model.addAttribute("campus", campus);
        model.addAttribute("universities", universities);
        sessionService.addSession(new Session("/addcampus",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return "addcampus";
    }

    @GetMapping("/acampus")
    @AdminOnly
    public ModelAndView acampus(@RequestParam("campusId")Long campusId, HttpSession httpSession){
        var modelAndView = new ModelAndView();
        var campus = campusService.getCampusById(campusId);
        var teachers = teacherService.getTeachersOfCampus(campusId);
        modelAndView.getModel().put("campus",campus);
        modelAndView.getModel().put("teachers", teachers);
        sessionService.addSession(new Session("/acampus",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return modelAndView;
    }
}