package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityCampusesCountException extends DomainException {

  private static final String MESSAGE_NEGATIVE =
      "The university campuses count must not be negative.";

  private InvalidUniversityCampusesCountException(final String message) {
    super(message);
  }

  public static InvalidUniversityCampusesCountException becauseValueIsNegative() {
    return new InvalidUniversityCampusesCountException(MESSAGE_NEGATIVE);
  }
}
