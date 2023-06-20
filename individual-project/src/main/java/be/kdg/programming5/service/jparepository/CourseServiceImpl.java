package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.presentation.mvc.viewmodels.CourseViewModel;
import be.kdg.programming5.repository.jparepository.CourseRepository;
import be.kdg.programming5.service.interfaces.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CourseViewModel> getCoursesByTeacherIdAndCampusId(Long teacherId, Long campusId) {
        return courseRepository.findAllByTeacherIdAndCampusId(teacherId, campusId)
                .stream()
                .map(course -> modelMapper.map(course, CourseViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
