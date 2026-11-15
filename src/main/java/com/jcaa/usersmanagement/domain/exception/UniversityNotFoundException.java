package com.jcaa.usersmanagement.domain.exception;

public final class UniversityNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID = "The university with id '%s' was not found.";

  private UniversityNotFoundException(final String message) {
    super(message);
  }

  public static UniversityNotFoundException becauseIdWasNotFound(final String universityId) {
    return new UniversityNotFoundException(String.format(MESSAGE_BY_ID, universityId));
  }
}
