package be.kdg.programming5.repository;



import be.kdg.programming5.domain.*;
import be.kdg.programming5.repository.jparepository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository sut;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private UniversityRepository universityRepository;

    private long teacherId;
    private long userId;
    private long course1Id;
    private long course2Id;
    private long course3Id;
    private long campusId;
    private String universityName;


    @BeforeAll
    public void setup() {
        // Arrange
        var teacher = new Teacher();
        var course1 = new Course();
        var course2 = new Course();
        var course3 = new Course();
        var user = new User();
        var campus = new Campus();
        var university = new University();

        course1.setTeacher(teacher);
        course1.setCampus(campus);
        course2.setTeacher(teacher);
        course2.setCampus(campus);
        course3.setTeacher(teacher);
        course3.setCampus(campus);
        teacher.setUser(user);
        university.setName("some university");
        campus.setUniversity(university);

        userId = userRepository.save(user).getId();
        teacherId = sut.save(teacher).getId();
        universityName = universityRepository.save(university).getName();
        campusId = campusRepository.save(campus).getId();
        course1Id = courseRepository.save(course1).getId();
        course2Id = courseRepository.save(course2).getId();
        course3Id = courseRepository.save(course3).getId();
    }
    @Test
    public void deleteTeacherShouldAlsoDeleteAssociatedCoursesAndUserAccount() {
        // Arrange
        var teacher = sut.findById(teacherId).get();

        // Act
        sut.delete(teacher);

        // Assert
        assertFalse(sut.existsById(teacherId));
        assertFalse(userRepository.existsById(userId));
        assertFalse(courseRepository.existsById(course1Id));
        assertFalse(courseRepository.existsById(course2Id));
        assertFalse(courseRepository.existsById(course3Id));
    }

    @AfterAll
    public void tearDown() {
        if (courseRepository.existsById(course1Id))
            courseRepository.deleteById(course1Id);
        if (courseRepository.existsById(course2Id))
            courseRepository.deleteById(course2Id);
        if (courseRepository.existsById(course3Id))
            courseRepository.deleteById(course3Id);
        if (sut.existsById(teacherId))
            sut.deleteById(teacherId);
        if (userRepository.existsById(userId))
            userRepository.deleteById(userId);
        if (campusRepository.existsById(campusId))
            campusRepository.deleteById(campusId);
        if (universityRepository.existsById(universityName))
            universityRepository.deleteById(universityName);
    }
}
