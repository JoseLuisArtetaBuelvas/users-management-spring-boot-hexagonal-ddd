package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityCareersCountException;

public record UniversityCareersCount(int value) {

  public UniversityCareersCount {
    validateNotNegative(value);
  }

  private static void validateNotNegative(final int value) {
    if (value < 0) {
      throw InvalidUniversityCareersCountException.becauseValueIsNegative();
    }
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }
}
