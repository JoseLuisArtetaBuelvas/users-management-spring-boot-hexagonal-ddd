package com.jcaa.usersmanagement.domain.valueobject;
import com.jcaa.usersmanagement.domain.exception.InvalidUniversityNameException;
import java.util.Objects;
public record UniversityName(String value) {
    private static final int MINIMUN_LENGTH = 3;
    private static final int MAXIMUS_LENGHT = 20;

    public UniversityName{
        final String normalizeValue = Objects.requireNonNull(value, "UniversityName cannot be null").trim();
        validateNotEmpty(normalizeValue);
        validateMinumunLenght(normalizeValue);
        validateMaximusLenght(normalizeValue);
        value = normalizeValue;
    }

    private static void validateNotEmpty(final String normalizeValue){
        if(normalizeValue.isEmpty()){
            throw InvalidUniversityNameException.becauseValueIsEmpty();
        }
    }

    private static void validateMinumunLenght(final String normalizeValue){
        if(normalizeValue.length() < MINIMUN_LENGTH){
            throw InvalidUniversityNameException.becauseLenghtIsTooShort(MINIMUN_LENGTH);
        }
    }

    private static void validateMaximusLenght(final String normalizeValue){
        if(normalizeValue.length() > MAXIMUS_LENGHT){
            throw InvalidUniversityNameException.becauseLengthIsTooLong(MAXIMUS_LENGHT);
        }
    }

    @Override
    public String toString(){return value;}
}
