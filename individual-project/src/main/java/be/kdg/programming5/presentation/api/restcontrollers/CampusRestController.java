package be.kdg.programming5.presentation.api.restcontrollers;

import be.kdg.programming5.presentation.api.dtos.CampusDto;
import be.kdg.programming5.presentation.api.dtos.NewCampusDto;
import be.kdg.programming5.presentation.api.dtos.UpdateCampusDto;
import be.kdg.programming5.security.AdminOnly;
import be.kdg.programming5.service.interfaces.CampusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/campuses")
public class CampusRestController {
    private final CampusService campusService;
    private final Logger logger = LoggerFactory.getLogger(CampusRestController.class);
    public CampusRestController(CampusService campusService) {
        this.campusService = campusService;
    }

    @PostMapping
    @AdminOnly
    public ResponseEntity<CampusDto> createNewCampus(@Valid @RequestBody NewCampusDto newCampusDto,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Admin with username {} has submitted a request to create a new campus...", userDetails.getUsername());

        var campus = campusService.addCampus(newCampusDto);

        if (campus != null) {
            logger.info("The campus has been created.");
            return new ResponseEntity<>(campus, HttpStatus.CREATED);
        }
        else {
            logger.warn("The campus hasn't been created.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    @AdminOnly
    public ResponseEntity<Void> updateCampus(@PathVariable long id,
                                             @Valid @RequestBody UpdateCampusDto updateCampusDto,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Admin with username {} has submitted a request to update a campus...", userDetails.getUsername());

        if (campusService.updateCampus(id, updateCampusDto)) {
            logger.info("The campus has been updated.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("The campus hasn't been updated.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @AdminOnly
    public ResponseEntity<Void> deleteCampus(@PathVariable long id,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Admin with username {} has submitted a request to delete a campus...", userDetails.getUsername());

        if (campusService.campusExists(id)) {
            campusService.deleteCampusAndAssociatedCourses(id);
            logger.info("The campus has been deleted.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        logger.warn("The campus doesn't exist.");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<CampusDto>> searchCampuses(@RequestParam String searchValue) {
        var campuses = campusService.searchCampuses(searchValue);
        if (campuses == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(campuses, HttpStatus.OK);
        }
    }
}
