package com.jcaa.usersmanagement.domain.exception;

public class InvalidUniversityIdException extends RuntimeException {
    private static final String MESSAGE_EMPTY  = "The univertsity id must not be empty";

    public InvalidUniversityIdException(String message) {
        super(message);
    }

    public static InvalidUniversityIdException becauseValueIsEmpty(){
        return new InvalidUniversityIdException(MESSAGE_EMPTY);
    }

}
