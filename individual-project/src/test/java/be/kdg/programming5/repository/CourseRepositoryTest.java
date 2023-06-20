package be.kdg.programming5.repository;

import be.kdg.programming5.domain.*;
import be.kdg.programming5.repository.jparepository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CourseRepositoryTest {
    @Autowired
    private CourseRepository sut;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private UniversityRepository universityRepository;

    private long teacherId;
    private long courseId;
    private long campusId;
    private String universityName;


    @BeforeAll
    public void setup() {
        var teacher = new Teacher();
        var course = new Course();
        var campus = new Campus();
        var university = new University();

        course.setTeacher(teacher);
        course.setCampus(campus);
        university.setName("some university");
        campus.setUniversity(university);

        teacherId = teacherRepository.save(teacher).getId();
        universityName = universityRepository.save(university).getName();
        campusId = campusRepository.save(campus).getId();
        courseId = sut.save(course).getId();
    }
    @Test
    public void campusAndTeacherInCourseShouldThrowExceptionWhenEitherIsSetToNull() {
        // Arrange
        var course = sut.findById(courseId).get();

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            // Act
            course.setTeacher(null);
            sut.save(course);
        });

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            // Act
            course.setCampus(null);
            sut.save(course);
        });
    }

    @AfterAll
    public void tearDown() {
        if (sut.existsById(courseId))
            sut.deleteById(courseId);
        if (teacherRepository.existsById(teacherId))
            teacherRepository.deleteById(teacherId);
        if (campusRepository.existsById(campusId))
            campusRepository.deleteById(campusId);
        if (universityRepository.existsById(universityName))
            universityRepository.deleteById(universityName);
    }
}
