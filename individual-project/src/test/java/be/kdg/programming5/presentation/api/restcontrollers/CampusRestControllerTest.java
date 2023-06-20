package be.kdg.programming5.presentation.api.restcontrollers;

import be.kdg.programming5.domain.Campus;
import be.kdg.programming5.domain.University;
import be.kdg.programming5.domain.enums.UniversityType;
import be.kdg.programming5.repository.jparepository.CampusRepository;
import be.kdg.programming5.repository.jparepository.UniversityRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

@AutoConfigureMockMvc
@SpringBootTest
class CampusRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    UniversityRepository universityRepository;
    private Campus testCampus;
    private University testUniversity;

    @BeforeEach
    void setup() {
        testCampus = new Campus("Test Campus", "Test Address", 12345, "Test City", LocalTime.of(8,0), LocalTime.of(20,0));
        testUniversity = universityRepository.save(new University("some_existent_university", LocalDate.now(), UniversityType.UNIVERSITEIT));
        testCampus.setUniversity(testUniversity);
        testCampus = campusRepository.save(testCampus);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCampusShouldReturnNoContentForExistingCampus() throws Exception {
        mockMvc.perform(delete("/api/campuses/{id}",testCampus.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCampusShouldReturnNotFoundForNonExistingCampus() throws Exception{
        // Assert
        var nonExistentCampusId = -1L;
        if (campusRepository.existsById(nonExistentCampusId))
            campusRepository.deleteById(nonExistentCampusId);

        mockMvc.perform(delete("/api/campuses/{id}",nonExistentCampusId)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteCampusShouldReturnForbiddenWithoutAdminRole() throws Exception {
        mockMvc.perform(delete("/api/campuses/{id}",testCampus.getId())
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @AfterEach
    void tearDown() {
        if (campusRepository.existsById(testCampus.getId()))
            campusRepository.deleteById(testCampus.getId());
        if (universityRepository.existsById(testUniversity.getName()))
            universityRepository.deleteById(testUniversity.getName());
    }
}
