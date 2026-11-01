package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUniversityIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The university id must not be empty.";

  private InvalidUniversityIdException(final String message) {
    super(message);
  }

  public static InvalidUniversityIdException becauseValueIsEmpty() {
    return new InvalidUniversityIdException(MESSAGE_EMPTY);
  }
}
