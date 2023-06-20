package be.kdg.programming5.service.interfaces;

import be.kdg.programming5.presentation.api.dtos.CampusDto;
import be.kdg.programming5.presentation.api.dtos.NewCampusDto;
import be.kdg.programming5.presentation.api.dtos.UpdateCampusDto;
import be.kdg.programming5.presentation.mvc.viewmodels.CampusViewModel;
import java.util.List;

public interface CampusService {
    List<CampusViewModel> getAllCampuses();
    CampusViewModel getCampusById(long id);
    List<CampusViewModel> getJobLocationsOfTeacher(long teacherId);
    void deleteCampusAndAssociatedCourses(long id);
    boolean campusExists(long id);
    CampusDto addCampus(NewCampusDto newCampusDto);
    boolean updateCampus(long id, UpdateCampusDto updateCampusDto);

    List<CampusDto> searchCampuses(String searchValue);
}
