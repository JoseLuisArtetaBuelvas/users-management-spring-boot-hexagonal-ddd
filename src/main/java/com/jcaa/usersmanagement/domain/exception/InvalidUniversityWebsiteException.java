package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityWebsiteException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university website must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT =
      "The university website '%s' does not have a valid format.";
  private static final String MESSAGE_TOO_LONG =
      "The university website must have at most %d characters.";

  private InvalidUniversityWebsiteException(final String message) {
    super(message);
  }

  public static InvalidUniversityWebsiteException becauseValueIsEmpty() {
    return new InvalidUniversityWebsiteException(MESSAGE_EMPTY);
  }

  public static InvalidUniversityWebsiteException becauseFormatIsInvalid(final String value) {
    return new InvalidUniversityWebsiteException(String.format(MESSAGE_INVALID_FORMAT, value));
  }

  public static InvalidUniversityWebsiteException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidUniversityWebsiteException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
