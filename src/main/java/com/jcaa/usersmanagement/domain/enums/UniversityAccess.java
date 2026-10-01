package com.jcaa.usersmanagement.domain.enums;

public enum UniversityAccess {
    EXAMEN_ADMISION,
    ICFES;


    public static UniversityAccess fromString(final String value) {
        for (final UniversityAccess access : values()) {
            if (access.name().equalsIgnoreCase(value)) {
                return access;
            }
        }
        //Aca va la exeption
        return null;
    }
}
