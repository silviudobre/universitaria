package be.kdg.programming5.service.interfaces;

import be.kdg.programming5.presentation.mvc.viewmodels.NewTeacherViewModel;
import be.kdg.programming5.presentation.mvc.viewmodels.TeacherViewModel;

import java.util.List;

public interface TeacherService {
    List<TeacherViewModel> getAllTeachers();
    void addTeacher(NewTeacherViewModel newTeacherViewModel);
    TeacherViewModel getTeacherById(long id);
    List<TeacherViewModel> getTeachersOfCampus(long campusId);
    void deleteTeacherWithAssociatedCoursesAndUserAccount(long id);
    boolean isTeacherRequestingHisData(long teacherId, long userId);
    TeacherViewModel getTeacherByUserId(long userId);
}
