package be.kdg.programming5.presentation.api.restcontrollers;

import be.kdg.programming5.domain.Campus;
import be.kdg.programming5.presentation.api.dtos.CampusDto;
import be.kdg.programming5.presentation.api.dtos.NewCampusDto;
import be.kdg.programming5.service.interfaces.CampusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CampusRestControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    private CampusService campusService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCampusShouldReturnCreatedForValidCampus() throws Exception{
        // Arrange
        var newCampusDto = modelMapper.map(new Campus("Test Campus", "Test Address 2", 12345, "Test City", LocalTime.of(8,0), LocalTime.of(20,0)), NewCampusDto.class);
        newCampusDto.setUniversityName("Karel de Grote");
        given(campusService.addCampus(any())).willReturn(new CampusDto());

        // Act
        mockMvc.perform(post("/api/campuses")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCampusDto))
                .with(csrf()))
                // Assert
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCampusShouldReturnBadRequestForInvalidCampus() throws Exception{
        // Arrange
        var newCampusDto = new NewCampusDto();
        given(campusService.addCampus(any())).willReturn(null);

        // Act
        mockMvc.perform(post("/api/campuses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCampusDto))
                        .with(csrf()))
                // Assert
                .andExpect(status().isBadRequest());
    }
}
