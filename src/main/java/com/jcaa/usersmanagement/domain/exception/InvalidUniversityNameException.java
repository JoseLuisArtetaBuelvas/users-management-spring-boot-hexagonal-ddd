package com.jcaa.usersmanagement.domain.exception;

public class InvalidUniversityNameException extends RuntimeException {
    private static final String MESSAGE_EMPTY = "The university name must not be empty";
    private static final String MESSAGE_TOO_SHORT = "The user name must have at least %d characters.";
    private static final String MESSAGE_TOO_LONG = "The username must have a maximum of %d characters.";

    public InvalidUniversityNameException(String message) {
        super(message);
    }

    public static InvalidUniversityNameException becauseValueIsEmpty(){
        return new InvalidUniversityNameException(MESSAGE_EMPTY);
    }

    public static  InvalidUniversityNameException becauseLenghtIsTooShort(final int minimunLength){
        return new InvalidUniversityNameException(String.format(MESSAGE_TOO_SHORT, minimunLength));
    }

    public static InvalidUniversityNameException becauseLengthIsTooLong(final int maximusLength){
        return new InvalidUniversityNameException(String.format(MESSAGE_TOO_LONG, maximusLength));
    }
}
