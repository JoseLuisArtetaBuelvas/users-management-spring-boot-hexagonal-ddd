package com.jcaa.usersmanagement.domain.valueobject;

import java.util.Objects;
import java.util.regex.Pattern;

public record UniversityEmail(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");


    public UniversityEmail{
        final String normalizeValue = Objects.requireNonNull(value, "UniversityEmail cannot be null").trim();
        validateNotEmpty(normalizeValue);
        validateFormat(normalizeValue);
        value = normalizeValue;
    }

    private static void validateNotEmpty(final String normalizeValue){
        if(normalizeValue.isEmpty()){
            //crear excepcion
        }
    }

    public static void validateFormat(final String normalizeValue){
        if(!EMAIL_PATTERN.matcher(normalizeValue).matches()){
            //crear exepcion
        }

    }

    @Override
    public String toString(){return value;}

}
