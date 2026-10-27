package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityPhoneException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university phone must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT =
      "The university phone must contain exactly %d digits.";

  private InvalidUniversityPhoneException(final String message) {
    super(message);
  }

  public static InvalidUniversityPhoneException becauseValueIsEmpty() {
    return new InvalidUniversityPhoneException(MESSAGE_EMPTY);
  }

  public static InvalidUniversityPhoneException becauseFormatIsInvalid(final int expectedLength) {
    return new InvalidUniversityPhoneException(
        String.format(MESSAGE_INVALID_FORMAT, expectedLength));
  }
}