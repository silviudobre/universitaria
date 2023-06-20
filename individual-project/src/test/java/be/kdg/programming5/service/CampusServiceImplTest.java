package be.kdg.programming5.service;

import be.kdg.programming5.domain.Campus;
import be.kdg.programming5.domain.University;
import be.kdg.programming5.domain.enums.UniversityType;
import be.kdg.programming5.presentation.api.dtos.NewCampusDto;
import be.kdg.programming5.presentation.api.dtos.UpdateCampusDto;
import be.kdg.programming5.repository.jparepository.CampusRepository;
import be.kdg.programming5.repository.jparepository.UniversityRepository;
import be.kdg.programming5.service.interfaces.CampusService;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CampusServiceImplTest {
    @Autowired
    private CampusService sut;
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private ModelMapper modelMapper;
    private Campus testCampus;
    private University testUniversity;

    @BeforeAll
    public void setup() {
        testCampus = new Campus("Test Campus", "Test Address", 12345, "Test City", LocalTime.of(8,0), LocalTime.of(20,0));
        testUniversity = universityRepository.save(new University("some_existent_university", LocalDate.now(), UniversityType.UNIVERSITEIT));
        testCampus.setUniversity(testUniversity);
        testCampus = campusRepository.save(testCampus);
    }

    @Test
    void addCampusShouldFailForNonExistentAssociatedUniversity() {
        // Arrange
        var newCampusDto = modelMapper.map(testCampus,NewCampusDto.class);
        if (universityRepository.existsById("some_random_non_existent_university")) {
            universityRepository.deleteById("some_random_non_existent_university");
        }
        newCampusDto.setUniversityName("some_random_non_existent_university");

        // Act
        var campusDto = sut.addCampus(newCampusDto);

        // Assert
        assertNull(campusDto);
    }

    @Test
    void addCampusShouldSucceedAndReturnCampusDtoForExistentAssociatedUniversity() {
        // Arrange
        var newCampusDto = modelMapper.map(testCampus,NewCampusDto.class);
        newCampusDto.setUniversityName("some_existent_university");

        // Act
        var campusDto = sut.addCampus(newCampusDto);

        // Assert
        var createdCampus = campusRepository.findById(campusDto.getId()).orElse(null);
        assertNotNull(campusDto);
        assertNotNull(createdCampus);
        assertEquals(newCampusDto.getName(), createdCampus.getName());
        assertEquals(newCampusDto.getAddress(), createdCampus.getAddress());
        assertEquals(newCampusDto.getPostalCode(), createdCampus.getPostalCode());
        assertEquals(newCampusDto.getCity(), createdCampus.getCity());
        assertEquals(newCampusDto.getOpeningTime(), createdCampus.getOpeningTime());
        assertEquals(newCampusDto.getClosingTime(), createdCampus.getClosingTime());

        // Arrange
        campusRepository.deleteById(campusDto.getId());
    }

    @Test
    void updateCampusShouldSucceedForNonNullAttributes() {
        // Arrange
        if (!campusRepository.existsById(testCampus.getId()))
            fail(String.format("void updateCampusShouldSucceedForNonNullAttributes() - campus with id {}, which should exist, doesn't exist", testCampus.getId()));
        var existentCampusId = testCampus.getId();
        var updateCampusDto = modelMapper.map(testCampus,UpdateCampusDto.class);

        // Act
        var isSuccessful = sut.updateCampus(existentCampusId, updateCampusDto);

        // Assert
        var updatedCampus = campusRepository.findById(existentCampusId).orElse(null);
        assertTrue(isSuccessful);
        assertNotNull(updatedCampus);
        assertEquals(updateCampusDto.getName(), updatedCampus.getName());
        assertEquals(updateCampusDto.getAddress(), updatedCampus.getAddress());
        assertEquals(updateCampusDto.getPostalCode(), updatedCampus.getPostalCode());
        assertEquals(updateCampusDto.getCity(), updatedCampus.getCity());
        assertEquals(updateCampusDto.getOpeningTime(), updatedCampus.getOpeningTime());
        assertEquals(updateCampusDto.getClosingTime(), updatedCampus.getClosingTime());
    }

    @Test
    void updateCampusShouldFailForNonExistentCampus() {
        // Arrange
        var updateCampusDto = modelMapper.map(testCampus,UpdateCampusDto.class);
        var nonExistentCampusId = -1L;
        if (campusRepository.existsById(nonExistentCampusId))
            campusRepository.deleteById(nonExistentCampusId);

        // Act
        var isSuccessful = sut.updateCampus(nonExistentCampusId, updateCampusDto);

        // Assert
        assertFalse(isSuccessful);
        assertEquals(Optional.empty(),campusRepository.findById(nonExistentCampusId));
    }

    @Test
    void updateCampusShouldNotUpdateAttributesWithNullValue() {
        // Arrange
        if (!campusRepository.existsById(testCampus.getId()))
            fail(String.format("void updateCampusShouldSucceedForNonNullAttributes() - campus with id {}, which should exist, doesn't exist", testCampus.getId()));
        var existentCampusId = testCampus.getId();
        var updateCampusDto = new UpdateCampusDto();

        // Act
        var updatedCampusBefore = campusRepository.findById(existentCampusId).orElse(null);
        var isSuccessful = sut.updateCampus(existentCampusId, updateCampusDto);

        // Assert
        var updatedCampusAfter = campusRepository.findById(existentCampusId).orElse(null);
        assertTrue(isSuccessful);
        assertNotNull(updatedCampusBefore);
        assertNotNull(updatedCampusAfter);

        assertNull(updateCampusDto.getName());
        assertNull(updateCampusDto.getAddress());
        assertNull(updateCampusDto.getPostalCode());
        assertNull(updateCampusDto.getCity());
        assertNull(updateCampusDto.getOpeningTime());
        assertNull(updateCampusDto.getClosingTime());

        assertEquals(updatedCampusBefore.getName(), updatedCampusAfter.getName());
        assertEquals(updatedCampusBefore.getAddress(), updatedCampusAfter.getAddress());
        assertEquals(updatedCampusBefore.getPostalCode(), updatedCampusAfter.getPostalCode());
        assertEquals(updatedCampusBefore.getCity(), updatedCampusAfter.getCity());
        assertEquals(updatedCampusBefore.getOpeningTime(), updatedCampusAfter.getOpeningTime());
        assertEquals(updatedCampusBefore.getClosingTime(), updatedCampusAfter.getClosingTime());
    }

    @AfterAll
    public void tearDown() {
        campusRepository.deleteById(testCampus.getId());
        universityRepository.deleteById(testUniversity.getName());
    }
}
