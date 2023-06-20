package be.kdg.programming5.presentation.api.restcontrollers;


import be.kdg.programming5.presentation.api.dtos.NewUniversityDto;
import be.kdg.programming5.presentation.api.dtos.UniversityDto;
import be.kdg.programming5.service.interfaces.UniversityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityRestController {
    private final UniversityService universityService;

    public UniversityRestController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UniversityDto>> searchUniversities(@RequestParam String searchValue) {
        var universities = universityService.searchUniversities(searchValue);
        if (universities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(universities, HttpStatus.OK);
        }
    }

    @GetMapping("/{universityName}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UniversityDto> getUniversity(@PathVariable String universityName) {
        var university = universityService.getUniversityByName(universityName);
        if (university == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(university, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<UniversityDto> createNewUniversity(@Valid @RequestBody NewUniversityDto newUniversityDto) {
        var university = universityService.addUniversity(newUniversityDto);

        if (university != null) {
            return new ResponseEntity<>(university, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
