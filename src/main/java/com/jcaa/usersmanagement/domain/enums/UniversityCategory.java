package com.jcaa.usersmanagement.domain.enums;

public enum UniversityCategory {
    PUBLICA,
    PRIVADA;

    public static UniversityCategory finalString(final String value){
        for(final UniversityCategory category : values()){
            if(category.name().equalsIgnoreCase(value)){
                return category;
            }
        }
        //Acá va la exception
        return null;
    }
}
