package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.domain.Teacher;
import be.kdg.programming5.domain.User;
import be.kdg.programming5.repository.jparepository.TeacherRepository;
import be.kdg.programming5.service.interfaces.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TeacherServiceImplUnitTest {
    @Autowired
    private TeacherService sut;
    @MockBean
    private TeacherRepository teacherRepository;

    @Test
    void isTeacherRequestingHisDataShouldReturnTrueIfTheUserAccountBelongsToHim() {
        // Arrange
        var teacherId = 2L;
        var userId = 1L;
        var userIdOfTeacher = 1L;
        var teacher = mock(Teacher.class);
        var user = mock(User.class);
        given(teacherRepository.findById(teacherId)).willReturn(Optional.of(teacher));
        given(teacher.getUser()).willReturn(user);
        given(user.getId()).willReturn(userIdOfTeacher);

        // Act
        var isTeachersData = sut.isTeacherRequestingHisData(teacherId,userId);

        // Assert
        assertTrue(isTeachersData);
        verify(teacherRepository).findById(teacherId);
    }

    @Test
    void isTeacherRequestingHisDataShouldReturnFalseIfTheUserAccountDoesNotBelongToHim() {
        // Arrange
        var teacherId = 2L;
        var userId = 1L;
        var userIdOfTeacher = 2L;
        var teacher = mock(Teacher.class);
        var user = mock(User.class);
        given(teacherRepository.findById(teacherId)).willReturn(Optional.of(teacher));
        given(teacher.getUser()).willReturn(user);
        given(user.getId()).willReturn(userIdOfTeacher);

        // Act
        var isTeachersData = sut.isTeacherRequestingHisData(teacherId,userId);

        // Assert
        assertFalse(isTeachersData);
        verify(teacher).getUser();
        verify(user).getId();
    }
}
