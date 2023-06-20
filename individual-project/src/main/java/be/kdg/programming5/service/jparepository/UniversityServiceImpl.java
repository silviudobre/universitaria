package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.domain.University;
import be.kdg.programming5.exceptions.UniversityNotFoundException;
import be.kdg.programming5.presentation.api.dtos.NewUniversityDto;
import be.kdg.programming5.presentation.api.dtos.UniversityDto;
import be.kdg.programming5.presentation.mvc.viewmodels.UniversityViewModel;
import be.kdg.programming5.repository.jparepository.UniversityRepository;
import be.kdg.programming5.service.interfaces.UniversityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository universityRepository;
    private final ModelMapper modelMapper;

    public UniversityServiceImpl(UniversityRepository universityRepository, ModelMapper modelMapper) {
        this.universityRepository = universityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UniversityViewModel> getAllUniversities() {
        return universityRepository.findAll()
                .stream()
                .map(university -> modelMapper.map(university, UniversityViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UniversityDto> searchUniversities(String searchValue) {
        if (!searchValue.isEmpty()) {
            var universities = universityRepository.findBySearchValue(searchValue)
                    .stream()
                    .map(university -> modelMapper.map(university,UniversityDto.class))
                    .toList();

            return universities.isEmpty() ? null : universities;
        }

        return null;
    }

    @Override
    public UniversityDto getUniversityByName(String name) {
        if (name == null)
            return null;

        var university = universityRepository.findById(name).orElseThrow(() -> new UniversityNotFoundException(String.format("University with name {} was not found!",name)));
        return modelMapper.map(university,UniversityDto.class);
    }

    @Override
    public UniversityDto addUniversity(NewUniversityDto newUniversityDto) {
        if (universityRepository.findById(newUniversityDto.getName()).isPresent())
            return null;

        var university = modelMapper.map(newUniversityDto, University.class);
        university = universityRepository.save(university);

        return modelMapper.map(university, UniversityDto.class);
    }
}
