package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.domain.Campus;
import be.kdg.programming5.exceptions.CampusNotFoundException;
import be.kdg.programming5.presentation.api.dtos.CampusDto;
import be.kdg.programming5.presentation.api.dtos.NewCampusDto;
import be.kdg.programming5.presentation.api.dtos.UpdateCampusDto;
import be.kdg.programming5.presentation.mvc.viewmodels.CampusViewModel;
import be.kdg.programming5.repository.jparepository.CampusRepository;
import be.kdg.programming5.repository.jparepository.CourseRepository;
import be.kdg.programming5.repository.jparepository.UniversityRepository;
import be.kdg.programming5.service.interfaces.CampusService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampusServiceImpl implements CampusService {
    private final CampusRepository campusRepository;
    private final UniversityRepository universityRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(CampusServiceImpl.class);

    public CampusServiceImpl(CampusRepository campusRepository, UniversityRepository universityRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.campusRepository = campusRepository;
        this.universityRepository = universityRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CampusViewModel> getAllCampuses() {
        return campusRepository.findAll()
                .stream()
                .map(campus -> modelMapper.map(campus, CampusViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CampusViewModel getCampusById(long id) {
        var campus = campusRepository.findById(id).orElseThrow(() -> new CampusNotFoundException(String.format("Campus with ID {} was not found!",id)));
        return modelMapper.map(campus,CampusViewModel.class);
    }

    @Override
    public List<CampusViewModel> getJobLocationsOfTeacher(long teacherId) {
        return courseRepository.findJobLocationsByTeacherId(teacherId)
                .stream()
                .map(campus -> modelMapper.map(campus, CampusViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCampusAndAssociatedCourses(long id) {
        logger.info("Deleting campus with id {} from its repository...", id);
        var campus = campusRepository.findById(id).orElseThrow(() -> new CampusNotFoundException(String.format("Campus with ID {} was not found!",id)));
        campusRepository.delete(campus);
    }

    @Override
    public boolean campusExists(long id) {
        return campusRepository.existsById(id);
    }

    @Override
    public CampusDto addCampus(NewCampusDto newCampusDto) {
        var campus = modelMapper.map(newCampusDto, Campus.class);

        var university = universityRepository.findById(newCampusDto.getUniversityName())
                .orElse(null);

        logger.info("Saving {} to its repository...", campus);
        if (university == null) {
            logger.warn("Process aborted: the provided university doesn't exist.");
            return null;
        }

        campus.setUniversity(university);
        campus = campusRepository.save(campus);
        return modelMapper.map(campus,CampusDto.class);
    }

    @Override
    public boolean updateCampus(long id, UpdateCampusDto updateCampusDto) {
        var campusToBeUpdated = campusRepository.findById(id).orElse(null);

        if (campusToBeUpdated == null) {
            logger.warn("Process aborted: campus with id {} doesn't exist.", id);
            return false;
        }
        logger.info("Updating {} in its repository...", campusToBeUpdated);

        if (updateCampusDto.getName() != null)
            campusToBeUpdated.setName(updateCampusDto.getName());

        if (updateCampusDto.getAddress() != null)
            campusToBeUpdated.setAddress(updateCampusDto.getAddress());

        if (updateCampusDto.getPostalCode() != null)
            campusToBeUpdated.setPostalCode(updateCampusDto.getPostalCode());

        if (updateCampusDto.getCity() != null)
            campusToBeUpdated.setCity(updateCampusDto.getCity());

        if (updateCampusDto.getOpeningTime() != null)
            campusToBeUpdated.setOpeningTime(updateCampusDto.getOpeningTime());

        if (updateCampusDto.getClosingTime() != null)
            campusToBeUpdated.setClosingTime(updateCampusDto.getClosingTime());

        campusRepository.save(campusToBeUpdated);
        return true;
    }

    @Override
    public List<CampusDto> searchCampuses(String searchValue) {
        if (searchValue.isEmpty()) {
            return null;
        }

        var campuses = campusRepository.findBySearchValue(searchValue)
                .stream()
                .map(campus -> modelMapper.map(campus, CampusDto.class))
                .toList();

        return campuses.isEmpty() ? null : campuses;

    }
}
