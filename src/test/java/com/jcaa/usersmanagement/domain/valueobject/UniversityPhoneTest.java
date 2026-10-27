package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcaa.usersmanagement.domain.exception.InvalidUniversityPhoneException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniversityPhoneTest {

  private static final String VALID_PHONE = "1234567890";

  @Test
  @DisplayName("Should create a valid phone when the value has exactly ten digits")
  void shouldCreateValidPhone() {
    // Arrange
    final String phone = VALID_PHONE;

    // Act
    final UniversityPhone result = new UniversityPhone(phone);

    // Assert
    assertEquals(phone, result.value());
  }

  @Test
  @DisplayName("Should trim surrounding whitespace when creating the phone")
  void shouldTrimSurroundingWhitespace() {
    // Arrange
    final String phone = "  " + VALID_PHONE + "  ";

    // Act
    final UniversityPhone result = new UniversityPhone(phone);

    // Assert
    assertEquals(VALID_PHONE, result.value());
  }

  @Test
  @DisplayName("Should throw NullPointerException when the value is null")
  void shouldThrowWhenValueIsNull() {
    // Arrange
    final String phone = null;
    final Executable execution = () -> new UniversityPhone(phone);

    // Act + Assert
    assertThrows(NullPointerException.class, execution);
  }

  @ParameterizedTest
  @DisplayName("Should throw becauseValueIsEmpty when the value is blank")
  @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
  void shouldThrowWhenValueIsBlank(final String phone) {
    // Arrange
    final Executable execution = () -> new UniversityPhone(phone);

    // Act + Assert
    assertThrows(InvalidUniversityPhoneException.class, execution);
  }

  @ParameterizedTest
  @DisplayName("Should throw becauseFormatIsInvalid when the value is not ten digits")
  @ValueSource(
      strings = {
        "123456789",
        "12345678901",
        "abcdefghij",
        "12345-6789",
        "+581234567890",
        "123456789a",
        "123 456 7890",
        "00000000000"
      })
  void shouldThrowWhenFormatIsInvalid(final String phone) {
    // Arrange
    final Executable execution = () -> new UniversityPhone(phone);

    // Act + Assert
    assertThrows(InvalidUniversityPhoneException.class, execution);
  }

  @Test
  @DisplayName("Should build distinct messages for empty and invalid format values")
  void shouldBuildDistinctMessages() {
    // Arrange
    final InvalidUniversityPhoneException emptyException =
        InvalidUniversityPhoneException.becauseValueIsEmpty();
    final InvalidUniversityPhoneException formatException =
        InvalidUniversityPhoneException.becauseFormatIsInvalid(10);

    // Assert
    assertAll(
        () -> assertEquals("The university phone must not be empty.", emptyException.getMessage()),
        () ->
            assertEquals(
                "The university phone must contain exactly 10 digits.",
                formatException.getMessage()),
        () -> assertNotEquals(emptyException.getMessage(), formatException.getMessage()));
  }

  @Test
  @DisplayName("Should treat two phones with the same digits as equal")
  void shouldTreatPhonesWithSameDigitsAsEqual() {
    // Arrange
    final UniversityPhone first = new UniversityPhone(VALID_PHONE);
    final UniversityPhone second = new UniversityPhone(VALID_PHONE);

    // Assert
    assertAll(
        () -> assertEquals(first, second),
        () -> assertEquals(first.hashCode(), second.hashCode()));
  }
}