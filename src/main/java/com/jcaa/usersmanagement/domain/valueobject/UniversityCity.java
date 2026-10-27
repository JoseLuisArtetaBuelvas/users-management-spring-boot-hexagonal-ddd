package com.jcaa.usersmanagement.domain.valueobject;
import java.util.Objects;

public record UniversityCity(String value) {
    private static final int MINIMUN_LENGTH = 3;
    private static final int MAXIMUS_LENGHT = 20;

    public UniversityCity{
        final String normalizeValue = Objects.requireNonNull(value, "UniversityCity cannot be null").trim();


    }
}
