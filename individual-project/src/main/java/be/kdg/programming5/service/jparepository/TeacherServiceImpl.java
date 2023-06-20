package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.domain.Teacher;
import be.kdg.programming5.exceptions.TeacherNotFoundException;
import be.kdg.programming5.presentation.mvc.viewmodels.NewTeacherViewModel;
import be.kdg.programming5.presentation.mvc.viewmodels.TeacherViewModel;
import be.kdg.programming5.repository.jparepository.CourseRepository;
import be.kdg.programming5.repository.jparepository.TeacherRepository;
import be.kdg.programming5.service.interfaces.TeacherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    public TeacherServiceImpl(TeacherRepository teacherRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TeacherViewModel> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> modelMapper.map(teacher, TeacherViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addTeacher(NewTeacherViewModel newTeacherViewModel) {
        logger.debug("Creating {} to repository...", newTeacherViewModel);
        var teacher = modelMapper.map(newTeacherViewModel, Teacher.class);
        teacherRepository.save(teacher);
    }

    @Override
    public TeacherViewModel getTeacherById(long id) {
        var teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with ID {} was not found!",id)));
        return modelMapper.map(teacher, TeacherViewModel.class);
    }

    @Override
    public List<TeacherViewModel> getTeachersOfCampus(long campusId) {
        return courseRepository.findTeachersByCampusId(campusId)
                .stream()
                .map(teacher -> modelMapper.map(teacher, TeacherViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTeacherWithAssociatedCoursesAndUserAccount(long id) {
        var teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with ID {} was not found!",id)));
        teacherRepository.delete(teacher);
    }

    @Override
    public boolean isTeacherRequestingHisData(long teacherId, long userId) {
        var teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with ID {} was not found!",teacherId)));
        return teacher.getUser().getId() == userId;
    }

    @Override
    public TeacherViewModel getTeacherByUserId(long userId) {
        var teacher = teacherRepository.findByUserId(userId).orElseThrow(() -> new TeacherNotFoundException(String.format("Teacher with user_id {} was not found!",userId)));
        return modelMapper.map(teacher, TeacherViewModel.class);
    }
}
