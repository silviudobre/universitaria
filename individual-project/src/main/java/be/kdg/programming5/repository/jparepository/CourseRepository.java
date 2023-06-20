package be.kdg.programming5.repository.jparepository;

import be.kdg.programming5.domain.Campus;
import be.kdg.programming5.domain.Course;
import be.kdg.programming5.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllByTeacherIdAndCampusId(Long teacherId, Long campusId);

    @Query("select distinct teacher from Teacher teacher " +
            "join Course course " +
            "on teacher.id = course.teacher.id " +
            "where course.campus.id = :campusId")
    List<Teacher> findTeachersByCampusId(long campusId);

    @Query("select distinct campus from Campus campus " +
            "join Course course " +
            "on campus.id = course.campus.id " +
            "where course.teacher.id = :teacherId")
    List<Campus> findJobLocationsByTeacherId(long teacherId);
}
