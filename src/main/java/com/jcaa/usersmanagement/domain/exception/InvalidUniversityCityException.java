package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityCityException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university city must not be empty.";
  private static final String MESSAGE_TOO_SHORT =
      "The university city must have at least %d characters.";
  private static final String MESSAGE_TOO_LONG =
      "The university city must have at most %d characters.";

  private InvalidUniversityCityException(final String message) {
    super(message);
  }

  public static InvalidUniversityCityException becauseValueIsEmpty() {
    return new InvalidUniversityCityException(MESSAGE_EMPTY);
  }

  public static InvalidUniversityCityException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUniversityCityException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }

  public static InvalidUniversityCityException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidUniversityCityException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
