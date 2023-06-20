package be.kdg.programming5.service.jparepository;

import be.kdg.programming5.domain.enums.UniversityType;
import be.kdg.programming5.presentation.api.dtos.NewUniversityDto;
import be.kdg.programming5.service.interfaces.UniversityCsvService;
import be.kdg.programming5.service.interfaces.UniversityService;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Service
public class UniversityCsvServiceImpl implements UniversityCsvService {
    private static final Logger logger = LoggerFactory.getLogger(UniversityCsvServiceImpl.class);
    private final UniversityService universityService;

    public UniversityCsvServiceImpl(UniversityService universityService) {
        this.universityService = universityService;
    }

    @Override
    @Async
    public void handleUniversitiesCsvUpload(InputStream inputStream) {
        var csvReader = new CSVReader(new InputStreamReader(inputStream));
        String[] line;
        try {
            while ((line = csvReader.readNext()) != null) {
                var newUniversityDto = new NewUniversityDto(line[0], LocalDate.parse(line[1]), UniversityType.valueOf(line[2]));
                universityService.addUniversity(newUniversityDto);
                logger.info(newUniversityDto.toString());
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException ignored) {

        }
    }
}
