package com.jcaa.usersmanagement.domain.valueobject;
import java.util.Objects;

public record UniversityRector(String value) {
    private static final int MINIMUN_LENGTH = 3;
    private static final int MAXIMUS_LENGHT = 20;

    public UniversityRector{
        final String normalizeValue = Objects.requireNonNull(value, "UniversityRector cannot be null").trim();
        validateNotEmpty(normalizeValue);
        validateMinumunLenght(normalizeValue);
        validateMaximusLenght(normalizeValue);
        value = normalizeValue;
    }

    private static void validateNotEmpty(final String normalizeValue){
        if(normalizeValue.isEmpty()){
            //Exepcion
        }
    }

    public static void validateMinumunLenght(final String normalizeValue){
        if(normalizeValue.length() < MINIMUN_LENGTH){
            //Excepcion
        }
    }

    public static void validateMaximusLenght(final String normalizeValue){
        if(normalizeValue.length() > MAXIMUS_LENGHT){
            //Excepcion
        }
    }

    @Override
    public String toString(){return value;}
}
