package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityCampusesCountException;

public record UniversityCampusesCount(int value) {

  public UniversityCampusesCount {
    validateNotNegative(value);
  }

  private static void validateNotNegative(final int value) {
    if (value < 0) {
      throw InvalidUniversityCampusesCountException.becauseValueIsNegative();
    }
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }
}
