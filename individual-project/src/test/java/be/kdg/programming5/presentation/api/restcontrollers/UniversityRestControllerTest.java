package be.kdg.programming5.presentation.api.restcontrollers;

import be.kdg.programming5.domain.University;
import be.kdg.programming5.domain.enums.UniversityType;
import be.kdg.programming5.repository.jparepository.UniversityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
class UniversityRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UniversityRepository universityRepository;
    private University testUniversity1;
    private University testUniversity2;


    @BeforeEach
    void setup() {
        testUniversity1 = universityRepository.save(new University("some_existent_university", LocalDate.of(2002,4,2), UniversityType.UNIVERSITEIT));
        testUniversity2 = universityRepository.save(new University("some_other_existent_university", LocalDate.of(2002,4,1), UniversityType.HOGESCHOOL));

    }

    @Test
    @WithMockUser
    void searchUniversityShouldFindCaseInsensitiveAndReturnOk() throws Exception {
        mockMvc.perform(get("/api/universities")
                        .queryParam("searchValue", "some_existent_university")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("some_existent_university")))
                .andExpect(jsonPath("$[0].foundingDate", equalTo("2002-04-02")))
                .andExpect(jsonPath("$[0].universityType", equalTo("Universiteit")));


        mockMvc.perform(get("/api/universities")
                        .queryParam("searchValue", "some_")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(jsonPath("$[0].name", equalTo("some_existent_university")))
                .andExpect(jsonPath("$[0].foundingDate", equalTo("2002-04-02")))
                .andExpect(jsonPath("$[0].universityType", equalTo("Universiteit")))
                .andExpect(jsonPath("$[1].name", equalTo("some_other_existent_university")))
                .andExpect(jsonPath("$[1].foundingDate", equalTo("2002-04-01")))
                .andExpect(jsonPath("$[1].universityType", equalTo("Hogeschool")));

        mockMvc.perform(get("/api/universities")
                        .queryParam("searchValue", "er_exis")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("some_other_existent_university")))
                .andExpect(jsonPath("$[0].foundingDate", equalTo("2002-04-01")))
                .andExpect(jsonPath("$[0].universityType", equalTo("Hogeschool")));
    }

    @Test
    @WithMockUser
    void searchUniversityShouldReturnNoContentWhenPatternNotMatched() throws Exception {
        mockMvc.perform(get("/api/universities")
                        .queryParam("searchValue", "some_k")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchUniversityShouldReturnForbiddenIfNotLoggedIn() throws Exception {
        mockMvc.perform(get("/api/universities")
                        .queryParam("searchValue", "some_")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @AfterEach
    void tearDown() {
        if (universityRepository.existsById(testUniversity1.getName()))
            universityRepository.deleteById(testUniversity1.getName());

        if (universityRepository.existsById(testUniversity2.getName()))
            universityRepository.deleteById(testUniversity2.getName());
    }
}
