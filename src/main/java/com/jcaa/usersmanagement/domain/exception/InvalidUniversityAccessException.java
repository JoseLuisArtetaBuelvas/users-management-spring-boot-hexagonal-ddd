package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityAccessException extends DomainException {

  private static final String MESSAGE_INVALID = "The university access '%s' is not valid.";

  private InvalidUniversityAccessException(final String message) {
    super(message);
  }

  public static InvalidUniversityAccessException becauseValueIsInvalid(final String value) {
    return new InvalidUniversityAccessException(String.format(MESSAGE_INVALID, value));
  }
}
