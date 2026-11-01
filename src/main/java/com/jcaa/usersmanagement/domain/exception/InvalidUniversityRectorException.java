package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityRectorException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university rector must not be empty.";
  private static final String MESSAGE_TOO_SHORT =
      "The university rector must have at least %d characters.";
  private static final String MESSAGE_TOO_LONG =
      "The university rector must have at most %d characters.";

  private InvalidUniversityRectorException(final String message) {
    super(message);
  }

  public static InvalidUniversityRectorException becauseValueIsEmpty() {
    return new InvalidUniversityRectorException(MESSAGE_EMPTY);
  }

  public static InvalidUniversityRectorException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUniversityRectorException(String.format(MESSAGE_TOO_SHORT, minimumLength));
  }

  public static InvalidUniversityRectorException becauseLengthIsTooLong(final int maximumLength) {
    return new InvalidUniversityRectorException(String.format(MESSAGE_TOO_LONG, maximumLength));
  }
}
