package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityNameException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university name must not be empty.";
  private static final String MESSAGE_TOO_SHORT =
      "The university name must have at least %d characters.";
  private static final String MESSAGE_TOO_LONG =
      "The university name must have at most %d characters.";

  private InvalidUniversityNameException(final String message) {
    super(message);
  }

  public static InvalidUniversityNameException becauseValueIsEmpty() {
    return new InvalidUniversityNameException(MESSAGE_EMPTY);
  }

  public static InvalidUniversityNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUniversityNameException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }

  public static InvalidUniversityNameException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidUniversityNameException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
