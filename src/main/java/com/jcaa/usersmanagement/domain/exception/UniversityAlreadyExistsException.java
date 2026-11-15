package com.jcaa.usersmanagement.domain.exception;

public final class UniversityAlreadyExistsException extends DomainException {

  private static final String MESSAGE_NAME_EXISTS = "A university with name '%s' already exists.";

  private UniversityAlreadyExistsException(final String message) {
    super(message);
  }

  public static UniversityAlreadyExistsException becauseNameAlreadyExists(final String name) {
    return new UniversityAlreadyExistsException(String.format(MESSAGE_NAME_EXISTS, name));
  }
}
