package be.kdg.programming5.util.converters;

import be.kdg.programming5.domain.enums.Sex;
import org.springframework.core.convert.converter.Converter;

public class StringToSexConverter implements Converter<String, Sex> {
    @Override
    public Sex convert(String source) {
        for (Sex sex : Sex.values()) {
            if (source.equalsIgnoreCase(sex.toString()))
                return sex;
        }

        return Sex.PREFER_NOT_TO_SAY;
    }
}