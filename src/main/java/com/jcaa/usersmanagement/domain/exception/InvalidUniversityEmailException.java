package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityEmailException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university email must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT =
      "The university email '%s' does not have a valid format.";
  private static final String MESSAGE_TOO_LONG =
      "The university email must have at most %d characters.";

  private InvalidUniversityEmailException(final String message) {
    super(message);
  }

  public static InvalidUniversityEmailException becauseValueIsEmpty() {
    return new InvalidUniversityEmailException(MESSAGE_EMPTY);
  }

  public static InvalidUniversityEmailException becauseFormatIsInvalid(final String value) {
    return new InvalidUniversityEmailException(String.format(MESSAGE_INVALID_FORMAT, value));
  }

  public static InvalidUniversityEmailException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidUniversityEmailException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
