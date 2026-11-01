package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityEmailException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record UniversityEmail(String value) {

  private static final int MAXIMUM_LENGTH = 150;
  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

  public UniversityEmail {
    final String normalizedValue =
        Objects.requireNonNull(value, "UniversityEmail cannot be null").trim().toLowerCase(Locale.ROOT);
    validateNotEmpty(normalizedValue);
    validateMaximumLength(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUniversityEmailException.becauseValueIsEmpty();
    }
  }

  private static void validateMaximumLength(final String normalizedValue) {
    if (normalizedValue.length() > MAXIMUM_LENGTH) {
      throw InvalidUniversityEmailException.becauseLengthIsTooLong(MAXIMUM_LENGTH);
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!EMAIL_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidUniversityEmailException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
