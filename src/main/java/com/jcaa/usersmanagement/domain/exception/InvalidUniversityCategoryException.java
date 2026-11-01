package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityCategoryException extends DomainException {

  private static final String MESSAGE_INVALID = "The university category '%s' is not valid.";

  private InvalidUniversityCategoryException(final String message) {
    super(message);
  }

  public static InvalidUniversityCategoryException becauseValueIsInvalid(final String value) {
    return new InvalidUniversityCategoryException(String.format(MESSAGE_INVALID, value));
  }
}
