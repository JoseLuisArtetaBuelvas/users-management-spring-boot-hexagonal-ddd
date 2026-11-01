package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityPhoneException;
import java.util.Objects;
import java.util.regex.Pattern;

public record UniversityPhone(String value) {

  private static final int EXPECTED_LENGTH = 10;
  private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");

  public UniversityPhone {
    final String normalizedValue =
        Objects.requireNonNull(value, "UniversityPhone cannot be null").trim();
    validateNotEmpty(normalizedValue);
    validateNumber(normalizedValue);
    value = normalizedValue;
  }

  private static void validateNotEmpty(final String normalizedValue) {
    if (normalizedValue.isBlank()) {
      throw InvalidUniversityPhoneException.becauseValueIsEmpty();
    }
  }

  private static void validateNumber(final String normalizedValue) {
    if (!PHONE_PATTERN.matcher(normalizedValue).matches()) {
      throw InvalidUniversityPhoneException.becauseFormatIsInvalid(EXPECTED_LENGTH);
    }
  }

  @Override
  public String toString() {
    return value;
  }
}
