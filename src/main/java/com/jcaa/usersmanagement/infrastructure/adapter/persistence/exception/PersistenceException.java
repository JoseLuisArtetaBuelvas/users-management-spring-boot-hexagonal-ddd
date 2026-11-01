package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save user with ID: '%s'.";
  private static final String MESSAGE_UPDATE = "Failed to update user with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find user with ID: '%s'.";
  private static final String MESSAGE_EMAIL = "Failed to find user with email: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all users.";
  private static final String MESSAGE_DELETE = "Failed to delete user with ID: '%s'.";
  private static final String MESSAGE_CONNECTION = "Could not establish database connection.";
  private static final String MESSAGE_UNIVERSITY_SAVE =
      "Failed to save university with ID: '%s'.";
  private static final String MESSAGE_UNIVERSITY_UPDATE =
      "Failed to update university with ID: '%s'.";
  private static final String MESSAGE_UNIVERSITY_FIND =
      "Failed to find university with ID: '%s'.";
  private static final String MESSAGE_UNIVERSITY_NAME =
      "Failed to find university with name: '%s'.";
  private static final String MESSAGE_UNIVERSITY_ALL = "Failed to retrieve all universities.";
  private static final String MESSAGE_UNIVERSITY_DELETE =
      "Failed to delete university with ID: '%s'.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE, userId), cause);
  }

  public static PersistenceException becauseUniversitySaveFailed(
      final String universityId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UNIVERSITY_SAVE, universityId), cause);
  }

  public static PersistenceException becauseUniversityUpdateFailed(
      final String universityId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UNIVERSITY_UPDATE, universityId), cause);
  }

  public static PersistenceException becauseUniversityFindByIdFailed(
      final String universityId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UNIVERSITY_FIND, universityId), cause);
  }

  public static PersistenceException becauseUniversityFindByNameFailed(
      final String name, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UNIVERSITY_NAME, name), cause);
  }

  public static PersistenceException becauseUniversityFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_UNIVERSITY_ALL, cause);
  }

  public static PersistenceException becauseUniversityDeleteFailed(
      final String universityId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UNIVERSITY_DELETE, universityId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CONNECTION, cause);
  }
}
