package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityCategoryException;
import java.text.Normalizer;
import java.util.Locale;

public enum UniversityCategory {
  PUBLICA,
  PRIVADA;

  public static UniversityCategory fromString(final String value) {
    final String normalizedValue = normalize(value);
    for (final UniversityCategory category : values()) {
      if (category.name().equals(normalizedValue)) {
        return category;
      }
    }
    throw InvalidUniversityCategoryException.becauseValueIsInvalid(value);
  }

  private static String normalize(final String value) {
    if (value == null) {
      return "";
    }
    return Normalizer.normalize(value, Normalizer.Form.NFD)
        .replaceAll("\\p{M}", "")
        .trim()
        .toUpperCase(Locale.ROOT)
        .replaceAll("[^A-Z0-9]+", "_")
        .replaceAll("^_+|_+$", "");
  }
}
