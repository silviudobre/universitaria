package be.kdg.programming5.presentation.mvc.controllers;

import be.kdg.programming5.domain.Session;
import be.kdg.programming5.presentation.mvc.viewmodels.NewTeacherViewModel;
import be.kdg.programming5.security.AdminOnly;
import be.kdg.programming5.security.CustomUserDetails;
import be.kdg.programming5.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class TeacherController {
    private final TeacherService teacherService;
    private final CampusService campusService;
    private final UserService userService;
    private final SessionService sessionService;
    private final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    public TeacherController(TeacherService teacherService, CampusService campusService, UserService userService, SessionService sessionService) {
        this.teacherService = teacherService;
        this.campusService = campusService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/teachers")
    @AdminOnly
    public ModelAndView showAllTeachers(HttpSession httpSession){
        logger.debug("Showing all teachers from service...");
        var modelAndView = new ModelAndView();
        var teachers = teacherService.getAllTeachers();
        modelAndView.setViewName("teachers");
        modelAndView.getModel().put("teachers", teachers);
        sessionService.addSession(new Session("/teachers",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return modelAndView;
    }

    @GetMapping("/deleteTeacher")
    @AdminOnly
    public String deleteTeacher(@RequestParam("teacherId")Long teacherId,
                                ModelAndView modelAndView){
        teacherService.deleteTeacherWithAssociatedCoursesAndUserAccount(teacherId);

        var remainingTeachers = teacherService.getAllTeachers();
        modelAndView.setViewName("teachers");
        modelAndView.getModel().put("teachers", remainingTeachers);
        return "redirect:/teachers";
    }

    @GetMapping("/addteacher")
    @AdminOnly
    public String showAddTeacherForm(HttpSession httpSession, Model model) {
        logger.debug("Showing form to add teacher...");
        var teacher = new NewTeacherViewModel();
        var campuses = campusService.getAllCampuses();
        model.addAttribute("teacher", teacher);
        model.addAttribute("campuses", campuses);
        sessionService.addSession(new Session("/addteacher",Timestamp.valueOf(LocalDateTime.now())));
        httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        return "addteacher";
    }

    @PostMapping("/addteacher")
    @AdminOnly
    public String processAddTeacher(@Valid @ModelAttribute("teacher") NewTeacherViewModel newTeacherViewModel, BindingResult errors, Model model) {
        if(errors.hasErrors()) {
            var campuses = campusService.getAllCampuses();
            model.addAttribute("campuses", campuses);
            return "addteacher";
        }

        logger.debug("Adding {} to service...", newTeacherViewModel);
        teacherService.addTeacher(newTeacherViewModel);
        return "redirect:/";
    }

    @GetMapping("/ateacher")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public ModelAndView ateacher(@RequestParam("teacherId")Long teacherId,
                                 @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 HttpSession httpSession){
        var modelAndView = new ModelAndView();
        try {
            var teacher = userService.isTeacherUser(customUserDetails.getUserId())
                    ? teacherService.getTeacherByUserId(customUserDetails.getUserId())
                    : teacherService.getTeacherById(teacherId);
            var jobLocations = campusService.getJobLocationsOfTeacher(teacher.getId());
            modelAndView.getModel().put("teacher", teacher);
            modelAndView.getModel().put("campuses", jobLocations);

            sessionService.addSession(new Session("/ateacher", Timestamp.valueOf(LocalDateTime.now())));
            httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int) httpSession.getAttribute("nrVisits") + 1);
        } catch (UsernameNotFoundException | NullPointerException  e) {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }
}
