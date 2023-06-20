package be.kdg.programming5.service.interfaces;

import be.kdg.programming5.presentation.api.dtos.NewUniversityDto;
import be.kdg.programming5.presentation.api.dtos.UniversityDto;
import be.kdg.programming5.presentation.mvc.viewmodels.UniversityViewModel;

import java.util.List;

public interface UniversityService {
    List<UniversityViewModel> getAllUniversities();

    List<UniversityDto> searchUniversities(String searchValue);

    UniversityDto getUniversityByName(String name);

    UniversityDto addUniversity(NewUniversityDto newUniversityDto);
}
