package com.jcaa.usersmanagement.infrastructure.adapter.persistence.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DatabaseConfigTest {

  @Test
  void shouldBuildMySqlUrlWithLegacyConstructor() {
    // Arrange
    final DatabaseConfig config = new DatabaseConfig("localhost", 3306, "crud_usuarios", "root", "x");

    // Act
    final String url = config.buildJdbcUrl();

    // Assert
    assertAll(
        () -> assertThat(config.driver()).isEqualTo(DatabaseConfig.DRIVER_MYSQL),
        () -> assertThat(url).startsWith("jdbc:mysql://localhost:3306/crud_usuarios?"));
  }

  @Test
  void shouldBuildPostgreSqlUrlWithSslMode() {
    // Arrange
    final DatabaseConfig config =
        new DatabaseConfig(
            "PostgreSQL", "aws-1-us-west-2.pooler.supabase.com", 5432, "postgres", "u", "p", "require");

    // Act
    final String url = config.buildJdbcUrl();

    // Assert
    assertThat(url)
        .isEqualTo("jdbc:postgresql://aws-1-us-west-2.pooler.supabase.com:5432/postgres?sslmode=require");
  }

  @Test
  void shouldBuildPostgreSqlUrlWithoutSslModeWhenBlank() {
    // Arrange
    final DatabaseConfig config =
        new DatabaseConfig("postgresql", "localhost", 5432, "postgres", "u", "p", "  ");

    // Act
    final String url = config.buildJdbcUrl();

    // Assert
    assertThat(url).isEqualTo("jdbc:postgresql://localhost:5432/postgres");
  }

  @Test
  void shouldRejectUnsupportedDriver() {
    // Act & Assert
    final IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> new DatabaseConfig("oracle", "localhost", 1521, "db", "u", "p", ""));

    assertThat(exception).hasMessageContaining("oracle");
  }
}
