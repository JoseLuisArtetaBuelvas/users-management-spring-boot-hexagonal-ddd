package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityCareersCountException extends DomainException {

  private static final String MESSAGE_NEGATIVE =
      "The university careers count must not be negative.";

  private InvalidUniversityCareersCountException(final String message) {
    super(message);
  }

  public static InvalidUniversityCareersCountException becauseValueIsNegative() {
    return new InvalidUniversityCareersCountException(MESSAGE_NEGATIVE);
  }
}
