package com.jcaa.usersmanagement.domain.valueobject;
import com.jcaa.usersmanagement.domain.exception.InvalidUniversityIdException;
import java.util.Objects;

public record UniversityId(String value) {
    public UniversityId{
        final String normalizeValue = Objects.requireNonNull(value, "University cannot be null").trim();
        validateNotEmpty(normalizeValue);
        value = normalizeValue;
    }

    private static void validateNotEmpty(final String normalizeValue){
        if(normalizeValue.isEmpty()){
            throw InvalidUniversityIdException.becauseValueIsEmpty();
        }
    }

    @Override
    public String toString(){return value;}
}
