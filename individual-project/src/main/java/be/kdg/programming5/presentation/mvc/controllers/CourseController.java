package be.kdg.programming5.presentation.mvc.controllers;

import be.kdg.programming5.domain.Session;
import be.kdg.programming5.security.AdminOnly;
import be.kdg.programming5.security.CustomUserDetails;
import be.kdg.programming5.service.interfaces.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final CampusService campusService;
    private final UserService userService;
    private final SessionService sessionService;

    public CourseController(CourseService courseService, TeacherService teacherService, CampusService campusService, UserService userService, SessionService sessionService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.campusService = campusService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/courses")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TEACHER')")
    public ModelAndView showCoursesBetweenTeacherAndCampus(@RequestParam("teacherId")Long teacherId,
                                                           @RequestParam("campusId")Long campusId,
                                                           @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                           HttpSession httpSession){
        var modelAndView = new ModelAndView();
        try {
            if (userService.isTeacherUser(customUserDetails.getUserId())
                    && !teacherService.isTeacherRequestingHisData(teacherId, customUserDetails.getUserId())){
                modelAndView.setViewName("index");
                return modelAndView;
            }

            var teacher = teacherService.getTeacherById(teacherId);
            var campus = campusService.getCampusById(campusId);
            var courses = courseService.getCoursesByTeacherIdAndCampusId(teacherId, campusId);
            modelAndView.getModel().put("teacher",teacher);
            modelAndView.getModel().put("campus",campus);
            modelAndView.getModel().put("courses",courses);

            sessionService.addSession(new Session("/courses", Timestamp.valueOf(LocalDateTime.now())));
            httpSession.setAttribute("nrVisits", (httpSession.getAttribute("nrVisits") == null) ? 0 : (int)httpSession.getAttribute("nrVisits") + 1);
        } catch (UsernameNotFoundException | NullPointerException  e) {
            modelAndView.setViewName("index");
        }

        return modelAndView;
    }

    @GetMapping("/deleteCourse")
    @AdminOnly
    public String deleteCourse(@RequestParam("courseId")Long courseId, @RequestParam("url") String url){
        courseService.deleteCourse(courseId);
        return "redirect:" + url;
    }
}
