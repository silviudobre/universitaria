package be.kdg.programming5.util.converters;

import be.kdg.programming5.domain.enums.UniversityType;
import org.springframework.core.convert.converter.Converter;

public class StringToUniversityTypeConverter implements Converter<String, UniversityType> {
    @Override
    public UniversityType convert(String source) {
        for (UniversityType universityType : UniversityType.values()) {
            if (source.equalsIgnoreCase(universityType.toString()))
                return universityType;
        }

        return null;
    }
}
