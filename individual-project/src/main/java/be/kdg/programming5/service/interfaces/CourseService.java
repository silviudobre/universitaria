package be.kdg.programming5.service.interfaces;

import be.kdg.programming5.presentation.mvc.viewmodels.CourseViewModel;

import java.util.List;

public interface CourseService {

    List<CourseViewModel> getCoursesByTeacherIdAndCampusId(Long teacherId, Long campusId);

    void deleteCourse(Long id);
}
