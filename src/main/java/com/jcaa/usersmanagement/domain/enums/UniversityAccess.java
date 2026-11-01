package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityAccessException;
import java.text.Normalizer;
import java.util.Locale;

public enum UniversityAccess {
  EXAMEN_ADMISION,
  ICFES,
  INGRESO_DIRECTO;

  public static UniversityAccess fromString(final String value) {
    final String normalizedValue = normalize(value);
    for (final UniversityAccess access : values()) {
      if (access.name().equals(normalizedValue)) {
        return access;
      }
    }
    return switch (normalizedValue) {
      case "EXAMEN_DE_ADMISION" -> EXAMEN_ADMISION;
      case "PRUEBAS_SABER_11_ICFES", "PRUEBAS_SABER_11", "SABER_11" -> ICFES;
      default -> throw InvalidUniversityAccessException.becauseValueIsInvalid(value);
    };
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
