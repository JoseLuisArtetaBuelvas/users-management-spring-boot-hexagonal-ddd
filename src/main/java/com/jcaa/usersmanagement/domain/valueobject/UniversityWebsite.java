package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityWebsiteException;
import java.util.Objects;
import java.util.regex.Pattern;

public record UniversityWebsite(String value) {

  private static final int MAXIMUM_LENGTH = 150;
  private static final Pattern WEBSITE_PATTERN =
      Pattern.compile("^https?://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/\\S*)?$");

  public UniversityWebsite {
    final String normalizedValue =
        Objects.requireNonNull(value, "UniversityWebsite cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateMaximumLength(normalizedValue);
    validateFormat(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isEmpty()) {
      throw InvalidUniversityWebsiteException.becauseValueIsEmpty();
    }
  }

  private static void validateMaximumLength(final String normalizedValue) {
    if (normalizedValue.length() > MAXIMUM_LENGTH) {
      throw InvalidUniversityWebsiteException.becauseLengthIsTooLong(MAXIMUM_LENGTH);
    }
  }

  private static void validateFormat(final String normalizedValue) {
    if (!WEBSITE_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidUniversityWebsiteException.becauseFormatIsInvalid(normalizedValue);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
